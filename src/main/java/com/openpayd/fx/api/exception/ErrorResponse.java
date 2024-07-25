package com.openpayd.fx.api.exception;

public class ErrorResponse {
    public final String error;
    public final String message;
    public final String cause;

    public ErrorResponse(String error, String message, String cause) {
        this.error = error;
        this.message = message;
        this.cause = cause;
    }
}
