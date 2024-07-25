package com.openpayd.fx.core.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FXCalculatorTests {

    @Test
    public void calculateTest() {
        // Arrange
        var calculator = new FXCalculator();
        var amount = 100.0;
        var exchangeRate = 1.25;
        var expected = amount * exchangeRate;

        // Act
        var result = calculator.calculate(amount, exchangeRate);

        // Assert
        assertEquals(expected, result);
    }
}
