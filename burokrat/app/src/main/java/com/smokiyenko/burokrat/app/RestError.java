package com.smokiyenko.burokrat.app;

/**
 * Created by s.mokiyenko on 9/7/14.
 */
public class RestError {

    private final String errorMessage;

    public RestError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
