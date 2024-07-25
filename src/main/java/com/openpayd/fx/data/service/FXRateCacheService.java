package com.openpayd.fx.data.service;

import com.openpayd.fx.data.entity.FXRateCache;
import com.openpayd.fx.data.repository.FXRateCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FXRateCacheService {

    private static final Logger logger = LoggerFactory.getLogger(FXRateCacheService.class);

    private final FXRateCacheRepository repo;

    public FXRateCacheService(FXRateCacheRepository repo) {
        this.repo = repo;
    }

    public FXRateCache create(String from, String to, double rate) {
        FXRateCache result = null;
        try {
            var entity = new FXRateCache();
            entity.fromCurrency = from;
            entity.toCurrency = to;
            entity.rate = rate;
            result = repo.save(entity);
            logger.info("Created rate 'cache', from {} to {} rate {}", from, to, rate);
        }
        catch (Exception e) {
            logger.error("Error creating rate 'cache', from {} to {} rate {}, message {}, exception {}", from, to, rate, e.getMessage(), e);
        }
        return result;
    }

    public FXRateCache get(String fromCurrency, String toCurrency) {
        return repo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
    }

}
