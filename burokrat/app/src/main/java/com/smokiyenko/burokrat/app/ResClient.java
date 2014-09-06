package com.smokiyenko.burokrat.app;

import android.util.Log;

import com.google.gson.Gson;

import de.greenrobot.event.EventBus;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class ResClient implements Closeable {

    private final EventBus eventBus;

    private final ThreadPoolExecutor requestRunner = new ThreadPoolExecutor(1, 1, 5000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));

    public enum RequestUrl {
        LOGIN(""),
        SEARCH(""),
        QUEUE("");

        private final String requestUrl;
        //TODO add Server url
        private final static String SERVER_URL = "";

        RequestUrl(final String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public URL getRequestURL() throws MalformedURLException {
            return new URL(SERVER_URL.concat(requestUrl));
        }

    }

    public ResClient(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private abstract class InternalHttpCall implements Runnable {

        private final RESTRequest request;
        private final RequestUrl requestUrl;

        private InternalHttpCall(final RESTRequest restRequest, final RequestUrl requestUrl){
            this.request = restRequest;
            this.requestUrl = requestUrl;
        }


        @Override
        public void run() {
            final HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) requestUrl.getRequestURL().openConnection();
                urlConnection.setRequestMethod(request.getRequestType().toString());
                if (request.getBody() != null) {
                   final OutputStream outputStream = urlConnection.getOutputStream();
                    outputStream.write(request.getBody().getBytes("UTF-8"));
                }
                final int responseCode = HttpUrlConnectionSupport.retrieveResponseCode(urlConnection);

                final String result;
                switch (responseCode) {
                    case HttpURLConnection.HTTP_OK:
                        result = urlConnectionToString(urlConnection);
                        break;
                    default:
                        throw new IOException("Unexpected response received: " + responseCode);
                }
                parseResponse(result);
            } catch (final Exception ex) {
                Log.e("ResClient", "InternalHttpCall exception", ex );
            }
        }

        protected String urlConnectionToString(final HttpURLConnection connection) throws IOException {
            return urlConnectionToString(connection, false);
        }

        protected String urlConnectionToString(final HttpURLConnection connection, boolean error) throws IOException {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        error ? connection.getErrorStream() : connection.getInputStream(), "UTF-8"));

                final StringBuilder jsonStringBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    jsonStringBuilder.append(inputLine);
                }
                return jsonStringBuilder.toString();
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }

        abstract void parseResponse(final String json);
    }


    public void login(final String passportSeries, final String passportId){

        final String body = String.format("passportSeries=%s,passportSeries=%s",passportId,passportSeries);
        final RESTRequest request = new RESTRequest.Builder().setBody(body).setType(RESTRequest.RequestType.POST).build();

        final InternalHttpCall httpCall = new InternalHttpCall(request,RequestUrl.LOGIN) {
            @Override
            void parseResponse(String json) {
                Gson gson = new Gson();
            }
        };

        requestRunner.submit(httpCall);
    }


    @Override
    public void close() throws IOException {
        requestRunner.shutdownNow();
    }
}
