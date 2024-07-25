package com.openpayd.fx.data.repository;

import com.openpayd.fx.data.entity.CurrencyConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface CurrencyConversionRepository  extends JpaRepository<CurrencyConversion, Long> {

    @Query("SELECT c FROM CurrencyConversion c WHERE c.uuid = :transactionID OR c.date = :transactionDate")
    Page<CurrencyConversion> findByUuidOrDate(@Param("transactionID") UUID transactionID, @Param("transactionDate") LocalDate transactionDate, Pageable pageable);

}
