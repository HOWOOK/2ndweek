package com.example.q.a2ndweek;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {

    private static WebService.ApiMethods REST_CLIENT;
    private static String ROOT = "http://52.231.67.98:8080";

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    public static WebService.ApiMethods get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ROOT).setClient(new OkClient(okHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        REST_CLIENT = restAdapter.create(WebService.ApiMethods.class);
    }

}
