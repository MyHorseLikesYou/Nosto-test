package com.example.nostotest.model;

import java.util.HashMap;

public class GetLatestCurrencyRatesResponse {
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
