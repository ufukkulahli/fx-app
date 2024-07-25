package com.openpayd.fx.api.model;

import com.openpayd.fx.api.model.response.RateResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RateResponseTests {

    @Test
    public void rateResponseFailTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";

        // Act
        var response = RateResponse.Fail(from, to);

        // Assert
        assertEquals(from, response.from);
        assertEquals(to, response.to);
        assertEquals(0, response.rate);
        assertFalse(response.success);
    }

    @Test
    public void rateResponseSuccessTest() {
        // Arrange
        var from = "USD";
        var to = "JPY";
        var rate = 110.0;

        // Act
        var response = RateResponse.Success(from, to, rate);

        // Assert
        assertEquals(from, response.from);
        assertEquals(to, response.to);
        assertEquals(rate, response.rate);
        assertTrue(response.success);
    }
}
