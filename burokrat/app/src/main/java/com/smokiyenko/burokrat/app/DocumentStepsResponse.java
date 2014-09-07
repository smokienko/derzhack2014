package com.smokiyenko.burokrat.app;

/**
 * Created by s.mokiyenko on 9/7/14.
 */
public class DocumentStepsResponse {

    private final String[] steps;

    public DocumentStepsResponse(String[] steps) {
        this.steps = steps;
    }

    public String[] getSteps() {
        return steps;
    }
}
