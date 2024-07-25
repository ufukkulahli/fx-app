package com.openpayd.fx.external.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openpayd.fx.external.config.FXAPIClientConfig;
import com.openpayd.fx.external.model.ExternalRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ExternalFXAPIRestClientTests {

    @Mock
    private FXAPIClientConfig config;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalFXAPIRestClient externalFXAPIRestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRateSuccessTest() {
        // Arrange
        String from = "USD";
        String to = "EUR";
        double rate = 0.85;

        when(config.fullUrl()).thenReturn("https://api.freecurrencyapi.com/v1/latest?apikey=1234&base_currency=USD&currencies=EUR");

        ExternalRateResponse response = new ExternalRateResponse();
        response.data.put("EUR", rate);

        when(restTemplate.getForEntity(anyString(), eq(ExternalRateResponse.class)))
        .thenReturn(ResponseEntity.ok(response));

        // Act
        ExternalRateResponse result = externalFXAPIRestClient.getRate(from, to);

        // Assert
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals(rate, result.Rate());
        verify(config).setFrom(from);
        verify(config).setTo(to);
    }

    @Test
    public void getRateFailureTest() {
        // Arrange
        String from = "USD";
        String to = "EUR";

        when(config.fullUrl()).thenReturn("https://api.freecurrencyapi.com/v1/latest?apikey=1234&base_currency=USD&currencies=EUR");

        when(restTemplate.getForEntity(anyString(), eq(ExternalRateResponse.class)))
                .thenThrow(new RuntimeException("API call failed"));

        // Act
        ExternalRateResponse result = externalFXAPIRestClient.getRate(from, to);

        // Assert
        assertNotNull(result);
        assertFalse(result.success);
        verify(config).setFrom(from);
        verify(config).setTo(to);
    }
}
