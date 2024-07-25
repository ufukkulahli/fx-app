package com.openpayd.fx.external.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ExternalRateResponseTests {

    private ExternalRateResponse response;

    @BeforeEach
    public void setUp() {
        response = new ExternalRateResponse();
    }

    @Test
    public void rateWithNonEmptyDataTest() {
        // Arrange
        Map<String, Double> data = new HashMap<>();
        data.put("USD", 1.12);
        response.data = data;

        // Act
        double rate = response.Rate();

        // Assert
        assertEquals(1.12, rate);
        assertTrue(response.success);
    }

    @Test
    public void rateWithEmptyDataTest() {
        // Arrange
        response.data = new HashMap<>();

        // Act
        double rate = response.Rate();

        // Assert
        assertEquals(0.0, rate, "Rate should be 0.0 when data is empty.");
        assertFalse(response.success, "Success should be false when data is empty.");
    }

    @Test
    public void failResponseTest() {
        // Act
        ExternalRateResponse failResponse = ExternalRateResponse.FailResponse();

        // Assert
        assertFalse(failResponse.success);
        assertTrue(failResponse.data.isEmpty(), "Data should be empty for fail response.");
    }
}
