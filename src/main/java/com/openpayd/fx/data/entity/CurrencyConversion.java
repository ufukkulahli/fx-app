package com.openpayd.fx.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class CurrencyConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String fromCurrency;
    public String toCurrency;
    public double rate;
    public double initialAmount;
    public double convertedAmount;
    public UUID uuid;
    public LocalDate date;
    public boolean success;
}
