package com.openpayd.fx.core.service;

import org.springframework.stereotype.Service;

@Service
public class FXCalculator {
    public double calculate(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }
}
