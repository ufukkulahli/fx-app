package com.openpayd.fx.external.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FXAPIClientConfigTests {

    private FXAPIClientConfig config;

    @BeforeEach
    public void setUp() {
        config = new FXAPIClientConfig();
    }

    @Test
    public void paramsTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        config.setFrom(from);
        config.setTo(to);

        var expectedParams =
                "?apikey=fca_live_Bj7xChlvMuZLtc2GvrKYMo38CjEwJHEQyg3WTfPV&base_currency=USD&currencies=EUR";

        // Act
        var result = config.params();

        // Assert
        assertEquals(expectedParams, result);
    }

    @Test
    public void fullUrlTest() {
        // Arrange
        var from = "USD";
        var to = "EUR";
        config.setFrom(from);
        config.setTo(to);

        var expectedFullUrl =
                "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_Bj7xChlvMuZLtc2GvrKYMo38CjEwJHEQyg3WTfPV&base_currency=USD&currencies=EUR";

        // Act
        var result = config.fullUrl();

        // Assert
        assertEquals(expectedFullUrl, result);
    }
}

