package com.openpayd.fx.data.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openpayd.fx.data.entity.FXRateCache;
import com.openpayd.fx.data.repository.FXRateCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FXRateCacheServiceTests {

    @Mock
    private FXRateCacheRepository repo;

    @InjectMocks
    private FXRateCacheService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        var rate = 0.85;

        var entity = new FXRateCache();
        entity.fromCurrency = from;
        entity.toCurrency = to;
        entity.rate = rate;

        when(repo.save(any(FXRateCache.class))).thenReturn(entity);

        // Act
        var result = service.create(from, to, rate);

        // Assert
        assertNotNull(result);
        assertEquals(from, result.fromCurrency);
        assertEquals(to, result.toCurrency);
        assertEquals(rate, result.rate);
        verify(repo).save(any(FXRateCache.class));
    }

    @Test
    public void getTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";

        var entity = new FXRateCache();
        entity.fromCurrency = from;
        entity.toCurrency = to;
        entity.rate = 0.85;

        when(repo.findByFromCurrencyAndToCurrency(from, to)).thenReturn(entity);

        // Act
        var result = service.get(from, to);

        // Assert
        assertNotNull(result);
        assertEquals(from, result.fromCurrency);
        assertEquals(to, result.toCurrency);
        assertEquals(0.85, result.rate);
        verify(repo).findByFromCurrencyAndToCurrency(from, to);
    }
}

