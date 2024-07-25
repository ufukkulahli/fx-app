package com.openpayd.fx.external.model;

import java.util.HashMap;
import java.util.Map;

public class ExternalRateResponse {
    public Map<String, Double> data = new HashMap<>();
    public boolean success = true;

    public double Rate() {
        if (data.isEmpty()) {
            this.success = false;
            return 0.0;
        }

        return data.entrySet().iterator().next().getValue();
    }

    public static ExternalRateResponse FailResponse() {
        var response = new ExternalRateResponse();
        response.success = false;
        return response;
    }
}
