package com.openpayd.fx.data.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openpayd.fx.data.entity.CurrencyConversion;
import com.openpayd.fx.data.repository.CurrencyConversionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CurrencyConversionHistoryServiceTests {

    @Mock
    private CurrencyConversionRepository repo;

    @InjectMocks
    private CurrencyConversionHistoryService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() {
        // Arrange
        var transactionID = UUID.randomUUID();
        var transactionDate = LocalDate.now();
        var page = 0;
        var size = 10;

        var conversion = new CurrencyConversion();
        conversion.fromCurrency = "USD";
        conversion.toCurrency = "EUR";
        conversion.rate = 0.85;
        conversion.initialAmount = 100.0;
        conversion.convertedAmount = 85.0;
        conversion.uuid = transactionID;
        conversion.date = transactionDate;
        Page<CurrencyConversion> pageResult = new PageImpl<>(List.of(conversion));

        when(repo.findByUuidOrDate(transactionID, transactionDate, PageRequest.of(page, size))).thenReturn(pageResult);

        // Act
        var result = service.getAll(transactionID, transactionDate, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        var resultConversion = result.getContent().get(0);
        assertEquals("USD", resultConversion.fromCurrency);
        assertEquals("EUR", resultConversion.toCurrency);
        assertEquals(0.85, resultConversion.rate);
        assertEquals(100.0, resultConversion.initialAmount);
        assertEquals(85.0, resultConversion.convertedAmount);
        assertEquals(transactionID, resultConversion.uuid);
        assertEquals(transactionDate, resultConversion.date);
        verify(repo).findByUuidOrDate(transactionID, transactionDate, PageRequest.of(page, size));
    }

    @Test
    public void createTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        var rate = 0.85;
        var initialAmount = 100.0;
        var convertedAmount = 85.0;
        var uuid = UUID.randomUUID();
        var date = LocalDate.now();
        var success = true;

        var entity = new CurrencyConversion();
        entity.fromCurrency = from;
        entity.toCurrency = to;
        entity.rate = rate;
        entity.initialAmount = initialAmount;
        entity.convertedAmount = convertedAmount;
        entity.uuid = uuid;
        entity.success = success;
        entity.date = date;

        when(repo.save(any(CurrencyConversion.class))).thenReturn(entity);

        // Act
        var result = service.create(from, to, rate, initialAmount, convertedAmount, uuid, date, success);

        // Assert
        assertNotNull(result);
        assertEquals(from, result.fromCurrency);
        assertEquals(to, result.toCurrency);
        assertEquals(rate, result.rate);
        assertEquals(initialAmount, result.initialAmount);
        assertEquals(convertedAmount, result.convertedAmount);
        assertEquals(uuid, result.uuid);
        assertEquals(date, result.date);
        assertEquals(success, result.success);
        verify(repo).save(any(CurrencyConversion.class));
    }
}

