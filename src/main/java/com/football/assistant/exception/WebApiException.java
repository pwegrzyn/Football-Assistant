package com.football.assistant.exception;

public class WebApiException extends Exception {

    private static final long serialVersionUID = 1L;

    public WebApiException() {}

    public WebApiException(String message) {
        super(message);
    }

    public WebApiException(Throwable cause) {
        super(cause);
    }

    public WebApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
