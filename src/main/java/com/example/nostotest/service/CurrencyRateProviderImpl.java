package com.example.nostotest.service;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;

public class CurrencyRateProviderImpl implements CurrencyRateProvider {

    private final String host;
    private final String apiKey;

    private final OkHttpClient client;
    private final Gson gson;

    public CurrencyRateProviderImpl(String host, String apiKey) {

        this.host = host;
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    @Override
    public double getRate(Currency source, Currency target) throws IOException {
        GetLatestCurrencyRatesResponse rates = GetLatestCurrencyRates();

        if (!rates.getBase().equals(source.getCurrencyCode())) {
            throw new UnsupportedOperationException("Currency " + source.getCurrencyCode() + " is not supported as a source");
        }

        if (!rates.getRates().containsKey(target.getCurrencyCode())) {
            throw new UnsupportedOperationException("Currency " + target.getCurrencyCode() + " is not supported as a target");
        }

        return rates.getRates().get(target.getCurrencyCode());
    }

    private GetLatestCurrencyRatesResponse GetLatestCurrencyRates() throws IOException {
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

class GetLatestCurrencyRatesResponse {
    private final String base;
    private final HashMap<String, Double> rates;

    public GetLatestCurrencyRatesResponse(String base, HashMap<String, Double> rates) {
        this.base = base;
        this.rates = rates;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public String getBase() {
        return base;
    }
}
