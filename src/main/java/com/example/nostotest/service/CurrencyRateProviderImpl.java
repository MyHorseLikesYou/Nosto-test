package com.example.nostotest.service;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;

public class CurrencyRateProviderImpl implements CurrencyRateProvider {
    @Override
    public double getRate(Currency source, Currency target) throws IOException {
        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient();

        String url = "http://api.exchangeratesapi.io/v1/latest?access_key=fdb0c2b963512318dd69f1b85d69d35f&symbols=USD,AUD,CAD,PLN,MXN&format=1";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            CurrencyRates rates = gson.fromJson(body, CurrencyRates.class);

            return rates.getRates().get(target.getCurrencyCode());
        }
    }
}

class CurrencyRates {
    private final String base;
    private final HashMap<String, Double> rates;

    public CurrencyRates(String base, HashMap<String, Double> rates) {
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
