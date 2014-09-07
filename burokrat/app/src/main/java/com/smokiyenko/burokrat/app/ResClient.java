package com.smokiyenko.burokrat.app;

import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
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
import java.util.concurrent.Executors;

/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class ResClient implements Closeable {

    private final EventBus eventBus;

    private final ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

    public enum RequestUrl {
        LOGIN("getUser"),
        SEARCH("getDocks"),
        QUEUE("");

        private final String requestUrl;
        //TODO add Server url
        private final static String SERVER_URL = "http://gs-rest-service2.cfapps.io/";

        RequestUrl(final String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public URL getRequestURL(final String body) throws MalformedURLException {
            return new URL(SERVER_URL.concat(requestUrl).concat(body));
        }

    }

    public ResClient(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private abstract class InternalHttpCall implements Runnable {

        private final RESTRequest request;
        private final RequestUrl requestUrl;

        private InternalHttpCall(final RESTRequest restRequest, final RequestUrl requestUrl) {
            this.request = restRequest;
            this.requestUrl = requestUrl;
        }


        @Override
        public void run() {
            final HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) requestUrl.getRequestURL(request.getBody()).openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod(request.getRequestType().toString());

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
                Log.e("ResClient", "InternalHttpCall exception", ex);
                throw new RuntimeException(ex);
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


    public void login(final String passportSeries, final String passportId) {

        final String body = String.format("?passportSeries=%s&passportId=%s", passportSeries, passportId);
        final RESTRequest request = new RESTRequest.Builder().setBody(body).setType(RESTRequest.RequestType.GET).build();

        final InternalHttpCall httpCall = new InternalHttpCall(request, RequestUrl.LOGIN) {
            @Override
            void parseResponse(String json) {
                Gson gson = new Gson();
                final SessionResponse session = gson.fromJson(json, SessionResponse.class);
                eventBus.postSticky(session);
            }
        };
        addCall(httpCall);

    }

    public void getDocksList(final SessionResponse sessionResponse) {
        final String body = String.format("?jsessionid=%s", sessionResponse.getJsessionid());
        final RESTRequest request = new RESTRequest.Builder().setBody(body).setType(RESTRequest.RequestType.GET).build();

        final InternalHttpCall httpCall = new InternalHttpCall(request, RequestUrl.LOGIN) {
            @Override
            void parseResponse(String json) {
                Gson gson = new Gson();
                final DocumentsListResponse documentsListResponse = gson.fromJson(json, DocumentsListResponse.class);
                eventBus.post(documentsListResponse);
            }
        };
        addCall(httpCall);
    }

    private void addCall(final InternalHttpCall call){
        final ListenableFuture future = service.submit(call);

        Futures.addCallback(future, new FutureCallback() {
            @Override
            public void onSuccess(final Object result) {
                Log.d("RestClient", "onSuccess");
                //We have allredy posted result to event bus
            }

            @Override
            public void onFailure(final Throwable t) {
                Log.e("RestClient", "onFailure", t);
                eventBus.post(new RestError(t.getMessage()));
            }
        });
    }


    @Override
    public void close() throws IOException {
        service.shutdownNow();
    }
}
