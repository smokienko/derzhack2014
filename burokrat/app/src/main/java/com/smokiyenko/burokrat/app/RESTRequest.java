package com.smokiyenko.burokrat.app;


/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class RESTRequest {

    private final RequestType requestType;

    private final String body;

    public enum RequestType {
        POST,
        GET;
    }

    private RESTRequest(final Builder builder) {
        this.requestType = builder.type;
        this.body = builder.body;
    }


    public static class Builder {

        private String body;
        private RequestType type;

        public Builder() {
        }

        public Builder setBody(final String body) {
            this.body = body;
            return this;
        }

        public Builder setType(final RequestType type) {
            this.type = type;
            return this;
        }

        public RESTRequest build() {
            return new RESTRequest(this);
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getBody() {
        return body;
    }

}
