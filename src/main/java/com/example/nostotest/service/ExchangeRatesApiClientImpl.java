package com.example.nostotest.service;

import com.example.nostotest.model.GetLatestCurrencyRatesResponse;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ExchangeRatesApiClientImpl implements ExchangeRatesApiClient {

    private final String host;
    private final String apiKey;

    private final OkHttpClient client;
    private final Gson gson;

    public ExchangeRatesApiClientImpl(String host, String apiKey) {

        this.host = host;
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public GetLatestCurrencyRatesResponse getLatest() throws IOException {
        String url = host +
                "latest" +
                "?access_key=" + apiKey +
                "&symbols=USD,AUD,CAD,PLN,MXN" +
                "&format=1";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            String body = response.body().string();
            return gson.fromJson(body, GetLatestCurrencyRatesResponse.class);
        }
    }
}
