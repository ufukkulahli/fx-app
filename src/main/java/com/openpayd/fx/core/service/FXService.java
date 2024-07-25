package com.openpayd.fx.core.service;

import com.openpayd.fx.api.model.response.ConversionResponse;
import com.openpayd.fx.api.model.response.RateResponse;
import com.openpayd.fx.data.service.CurrencyConversionHistoryService;
import com.openpayd.fx.data.service.FXRateCacheService;
import com.openpayd.fx.external.service.ExternalFXAPIRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FXService {

    private static final Logger logger = LoggerFactory.getLogger(FXService.class);

    private final CurrencyConversionHistoryService currencyConversionHistoryService;
    private final ExternalFXAPIRestClient externalFXAPIRestClient;
    private final FXCalculator fxCalculator;
    private final UniqueIdentifier uniqueIdentifier;
    private final FXRateCacheService fxRateCacheService;

    public FXService(CurrencyConversionHistoryService currencyConversionHistoryService,
                     ExternalFXAPIRestClient externalFXAPIRestClient,
                     FXCalculator fxCalculator,
                     UniqueIdentifier uniqueIdentifier,
                     FXRateCacheService fxRateCacheService) {
        this.currencyConversionHistoryService = currencyConversionHistoryService;
        this.externalFXAPIRestClient = externalFXAPIRestClient;
        this.fxCalculator = fxCalculator;
        this.uniqueIdentifier = uniqueIdentifier;
        this.fxRateCacheService = fxRateCacheService;
    }

    public RateResponse Rate(String from, String to){

        var cachedRate = fxRateCacheService.get(from, to);

        if (cachedRate == null) {

            logger.info("No 'cache' data, from {} to {}", from, to);

            var externalRateResponse = externalFXAPIRestClient.getRate(from, to);

            if (externalRateResponse.success) {
                logger.info("Fetched external rate, from {} to {} is {}", from, to, externalRateResponse.Rate());
                fxRateCacheService.create(from, to, externalRateResponse.Rate());
                return RateResponse.Success(from, to, externalRateResponse.Rate());
            }

            logger.error("Rate failed, from {} to {}", from, to);
            return RateResponse.Fail(from, to);
        }

        logger.info("Fetched 'cached' rate, from {} to {} rate is {}", from, to, cachedRate.rate);
        return RateResponse.Success(from, to, cachedRate.rate);
    }

    public ConversionResponse Convert(String from, String to, double initialAmount) {

        var rateResponse = this.Rate(from, to);

        if (rateResponse.success) {
            var convertedAmount = fxCalculator.calculate(initialAmount, rateResponse.rate);
            var uuid = uniqueIdentifier.getRandom();
            var now = LocalDate.now();
            currencyConversionHistoryService.create(from, to, rateResponse.rate, initialAmount, convertedAmount, uuid, now,true);

            logger.info("Convert from {} {} to {} is {} with a rate {}, transactionID {}",
                            from, initialAmount, to, convertedAmount, rateResponse.rate, uuid.toString());

            return ConversionResponse.Success(from, to, rateResponse.rate, initialAmount, convertedAmount, uuid, now);
        }

        var uuid = uniqueIdentifier.getRandom();
        var now = LocalDate.now();
        currencyConversionHistoryService.create(from, to, 0, initialAmount, 0, uuid, now, false);

        logger.error("Convert from {} {} to {} failed, transactionID {}",
                        from, initialAmount, to, uuid.toString());

        return ConversionResponse.Fail(from, to, initialAmount, uuid, now);
    }

    public List<ConversionResponse> History(UUID transactionID, LocalDate transactionDate, int page, int size) {

        var history = currencyConversionHistoryService.getAll(transactionID, transactionDate, page, size);

        List<ConversionResponse> results = new ArrayList<>();

        for (var conversion : history) {
            var result = new ConversionResponse
                    (conversion.fromCurrency, conversion.toCurrency, conversion.rate,
                     conversion.initialAmount, conversion.convertedAmount, conversion.uuid,
                     conversion.date, conversion.success);
            results.add(result);
        }

        return results;
    }

}
