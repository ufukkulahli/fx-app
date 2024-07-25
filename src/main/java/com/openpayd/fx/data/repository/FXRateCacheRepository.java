package com.openpayd.fx.data.repository;

import com.openpayd.fx.data.entity.FXRateCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FXRateCacheRepository extends JpaRepository<FXRateCache, Long> {
    FXRateCache findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
