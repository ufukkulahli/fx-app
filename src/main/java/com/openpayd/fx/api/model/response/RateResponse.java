package com.openpayd.fx.api.model.response;

public class RateResponse {
    public final String from;
    public final String to;
    public final double rate;
    public final boolean success;

    public RateResponse(String from, String to, double rate, boolean success) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.success = success;
    }

    public static RateResponse Fail(String from, String to) {
        return new RateResponse(from, to, 0, false);
    }

    public static RateResponse Success(String from, String to, double rate) {
        return new RateResponse(from, to, rate, true);
    }
}
