package com.openpayd.fx.api.model.response;

public class HealthCheckResponse {
    public boolean IsExternalFXAPIOK;
    public boolean IsInMemoryDBOK;

    @Override
    public String toString() {
        return "HealthCheckResponse{" +
                "IsExternalFXAPIOK=" + IsExternalFXAPIOK +
                ", IsInMemoryDBOK=" + IsInMemoryDBOK +
                '}';
    }
}
