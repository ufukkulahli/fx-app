package com.openpayd.fx.data.service;

import com.openpayd.fx.data.entity.CurrencyConversion;
import com.openpayd.fx.data.repository.CurrencyConversionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CurrencyConversionHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionHistoryService.class);

    private final CurrencyConversionRepository repo;

    public CurrencyConversionHistoryService(CurrencyConversionRepository repo) {
        this.repo = repo;
    }

    public Page<CurrencyConversion> getAll(UUID transactionID, LocalDate transactionDate, int page, int size) {
        return repo.findByUuidOrDate(transactionID, transactionDate, PageRequest.of(page, size));
    }

    public CurrencyConversion create
            (String from, String to, double rate, double initialAmount, double convertedAmount, UUID uuid, LocalDate date, boolean success) {
        CurrencyConversion result = null;
        try {
            var entity = new CurrencyConversion();
            entity.fromCurrency = from;
            entity.toCurrency = to;
            entity.rate = rate;
            entity.initialAmount = initialAmount;
            entity.convertedAmount = convertedAmount;
            entity.uuid = uuid;
            entity.success = success;
            entity.date = date;
            result = repo.save(entity);
        }
        catch (Exception e) {
            logger.error("Error creating currency conversion history record", e);
        }
        return result;
    }

}
