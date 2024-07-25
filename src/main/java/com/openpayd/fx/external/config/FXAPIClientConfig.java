package com.openpayd.fx.external.config;

import org.springframework.stereotype.Component;

@Component
public class FXAPIClientConfig {

    // TODO: READ CONFIG FROM A SECRET
    private final String url = "https://api.freecurrencyapi.com";
    private final String uri = "/v1/latest";
    private final String apiKey = "fca_live_Bj7xChlvMuZLtc2GvrKYMo38CjEwJHEQyg3WTfPV";

    private String from;
    private String to;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String params() {
        return "?apikey=" + apiKey + "&base_currency=" + from + "&currencies=" + to;
    }

    public String fullUrl() {
        return url + uri + params();
    }
}
