package com.openpayd.fx.api.validation;

public class ErrorResponse {
    public final String message;
    public final int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
