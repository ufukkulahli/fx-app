package com.openpayd.fx.core.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openpayd.fx.data.entity.CurrencyConversion;
import com.openpayd.fx.data.entity.FXRateCache;
import com.openpayd.fx.data.service.CurrencyConversionHistoryService;
import com.openpayd.fx.data.service.FXRateCacheService;
import com.openpayd.fx.external.model.ExternalRateResponse;
import com.openpayd.fx.external.service.ExternalFXAPIRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FXServiceTests {

    @Mock
    private CurrencyConversionHistoryService currencyConversionHistoryService;

    @Mock
    private ExternalFXAPIRestClient externalFXAPIRestClient;

    @Mock
    private FXCalculator fxCalculator;

    @Mock
    private UniqueIdentifier uniqueIdentifier;

    @Mock
    private FXRateCacheService fxRateCacheService;

    @InjectMocks
    private FXService fxService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void rateWithCachedDataTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        var rate = 0.85;

        var cachedRate = new FXRateCache();
        cachedRate.fromCurrency = from;
        cachedRate.toCurrency = to;
        cachedRate.rate = rate;

        when(fxRateCacheService.get(from, to)).thenReturn(cachedRate);

        // Act
        var result = fxService.Rate(from, to);

        // Assert
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals(from, result.from);
        assertEquals(to, result.to);
        assertEquals(rate, result.rate);
        verify(fxRateCacheService).get(from, to);
        verifyNoInteractions(externalFXAPIRestClient);
    }

    @Test
    public void rateWithoutCachedDataTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        var rate = 0.85;

        extRateResponse.success = true;

        when(extRateResponse.Rate()).thenReturn(rate);
        when(fxRateCacheService.get(from, to)).thenReturn(null);
        when(externalFXAPIRestClient.getRate(from, to)).thenReturn(extRateResponse);

        // Act
        var result = fxService.Rate(from, to);

        // Assert
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals(from, result.from);
        assertEquals(to, result.to);
        assertEquals(rate, result.rate);
        verify(fxRateCacheService).get(from, to);
        verify(externalFXAPIRestClient).getRate(from, to);
        verify(fxRateCacheService).create(from, to, rate);
    }

    @Test
    public void rateWithFailedExternalRateTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";

        extRateResponse.success = false;

        when(fxRateCacheService.get(from, to)).thenReturn(null);
        when(externalFXAPIRestClient.getRate(from, to)).thenReturn(extRateResponse);

        // Act
        var result = fxService.Rate(from, to);

        // Assert
        assertNotNull(result);
        assertFalse(result.success);
        assertEquals(from, result.from);
        assertEquals(to, result.to);
        verify(fxRateCacheService).get(from, to);
        verify(externalFXAPIRestClient).getRate(from, to);
    }

    @Mock
    ExternalRateResponse extRateResponse;

    @Test
    public void convertSuccessTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        var initialAmount = 100.0;
        var rate = 0.85;
        var convertedAmount = 85.0;
        var uuid = UUID.randomUUID();
        var date = LocalDate.now();

        var cachedRate = new FXRateCache();
        cachedRate.fromCurrency = from;
        cachedRate.toCurrency = to;
        cachedRate.rate = rate;

        when(fxRateCacheService.get(from, to)).thenReturn(cachedRate);
        when(extRateResponse.Rate()).thenReturn(rate);
        when(externalFXAPIRestClient.getRate(from, to)).thenReturn(extRateResponse);
        when(uniqueIdentifier.getRandom()).thenReturn(uuid);
        when(fxCalculator.calculate(initialAmount, rate)).thenReturn(convertedAmount);

        // Act
        var result = fxService.Convert(from, to, initialAmount);

        // Assert
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals(from, result.from);
        assertEquals(to, result.to);
        assertEquals(rate, result.rate);
        assertEquals(initialAmount, result.initialAmount);
        assertEquals(convertedAmount, result.convertedAmount);
        assertEquals(uuid, result.transactionID);
        assertEquals(date, result.date);
        verify(currencyConversionHistoryService).create(from, to, rate, initialAmount, convertedAmount, uuid, date, true);
    }

    @Test
    public void convertFailureTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        var initialAmount = 100.0;
        var uuid = UUID.randomUUID();
        var date = LocalDate.now();

        var externalRateResponse = new ExternalRateResponse();
        externalRateResponse.success = false;

        when(externalFXAPIRestClient.getRate(from, to)).thenReturn(externalRateResponse);
        when(uniqueIdentifier.getRandom()).thenReturn(uuid);

        // Act
        var result = fxService.Convert(from, to, initialAmount);

        // Assert
        assertNotNull(result);
        assertFalse(result.success);
        assertEquals(from, result.from);
        assertEquals(to, result.to);
        assertEquals(initialAmount, result.initialAmount);
        assertEquals(uuid, result.transactionID);
        assertEquals(date, result.date);
        verify(currencyConversionHistoryService).create(from, to, 0, initialAmount, 0, uuid, date, false);
    }

    @Test
    public void historyTest() {
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
        conversion.success = true;
        Page<CurrencyConversion> historyPage = new PageImpl<>(List.of(conversion));

        when(currencyConversionHistoryService.getAll(transactionID, transactionDate, page, size)).thenReturn(historyPage);

        // Act
        var result = fxService.History(transactionID, transactionDate, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        var conversionResponse = result.get(0);
        assertEquals(conversion.fromCurrency, conversionResponse.from);
        assertEquals(conversion.toCurrency, conversionResponse.to);
        assertEquals(conversion.rate, conversionResponse.rate);
        assertEquals(conversion.initialAmount, conversionResponse.initialAmount);
        assertEquals(conversion.convertedAmount, conversionResponse.convertedAmount);
        assertEquals(conversion.uuid, conversionResponse.transactionID);
        assertEquals(conversion.date, conversionResponse.date);
        assertEquals(conversion.success, conversionResponse.success);
    }
}
