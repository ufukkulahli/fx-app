package com.openpayd.fx.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FXRateCache {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String fromCurrency;
    public String toCurrency;
    public double rate;
}
