package com.openpayd.fx.api.model;

import com.openpayd.fx.api.model.response.ConversionResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ConversionResponseTests {

    private UUID transactionID;
    private LocalDate date;

    @Test
    public void conversionResponseSuccessTest() {
        // Act
        ConversionResponse response = ConversionResponse.Success(
                "USD",
                "JPY",
                110.0,
                100.0,
                11000.0,
                transactionID,
                date
        );

        // Assert
        assertEquals("USD", response.from);
        assertEquals("JPY", response.to);
        assertEquals(110.0, response.rate);
        assertEquals(100.0, response.initialAmount);
        assertEquals(11000.0, response.convertedAmount);
        assertEquals(transactionID, response.transactionID);
        assertEquals(date, response.date);
        assertTrue(response.success);
    }

    @Test
    public void conversionResponseFailTest() {
        // Act
        ConversionResponse response = ConversionResponse.Fail(
                "USD",
                "JPY",
                100.0,
                transactionID,
                date
        );

        // Assert
        assertEquals("USD", response.from);
        assertEquals("JPY", response.to);
        assertEquals(0.0, response.rate);
        assertEquals(100.0, response.initialAmount);
        assertEquals(0.0, response.convertedAmount);
        assertEquals(transactionID, response.transactionID);
        assertEquals(date, response.date);
        assertFalse(response.success);
    }
}

