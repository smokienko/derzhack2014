package com.smokiyenko.burokrat.app;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class HttpUrlConnectionSupport {

    public static int retrieveResponseCode(final HttpURLConnection urlConnection) throws IOException {
        // see http://stackoverflow.com/a/15973063: To be compatible with Android
        int responseCode;
        try {
            // Will throw IOException if server responds with 401.
            responseCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            // Will return 401, because now connection has the correct internal state.
            if (e.getMessage().contains("authentication challenge")) {
                responseCode = urlConnection.getResponseCode();
            } else {
                throw e;
            }
        }
        return responseCode;
    }
}
