package com.openpayd.fx.api.model.response;

import java.time.LocalDate;
import java.util.UUID;

public class ConversionResponse {
    public final String from;
    public final String to;
    public final double rate;
    public final double initialAmount;
    public final double convertedAmount;
    public final UUID transactionID;
    public final LocalDate date;
    public final boolean success;

    public ConversionResponse(
            String from,
            String to,
            double rate,
            double initialAmount,
            double convertedAmount,
            UUID transactionID,
            LocalDate date,
            boolean success) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.initialAmount = initialAmount;
        this.convertedAmount = convertedAmount;
        this.transactionID = transactionID;
        this.date = date;
        this.success = success;
    }

    public static ConversionResponse Success(String from,
                                             String to,
                                             double rate,
                                             double initialAmount,
                                             double convertedAmount,
                                             UUID uuid,
                                             LocalDate date) {
        return new ConversionResponse(from, to, rate, initialAmount, convertedAmount, uuid, date,true);
    }

    public static ConversionResponse Fail(String from,
                                          String to,
                                          double initialAmount,
                                          UUID uuid,
                                          LocalDate date){
        return new ConversionResponse(from, to, 0, initialAmount, 0, uuid, date,false);
    }
}

