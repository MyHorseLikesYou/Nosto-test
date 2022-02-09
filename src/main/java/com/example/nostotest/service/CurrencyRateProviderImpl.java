package com.example.nostotest.service;

import com.example.nostotest.model.GetLatestCurrencyRatesResponse;

import java.io.IOException;
import java.util.Currency;

public class CurrencyRateProviderImpl implements CurrencyRateProvider {

    private final ExchangeRatesApiClient client;

    public CurrencyRateProviderImpl(ExchangeRatesApiClient client) {
        this.client = client;
    }

    @Override
    public double getRate(Currency from, Currency to) throws IOException {
        GetLatestCurrencyRatesResponse rates = client.getLatest();

        if (!rates.getBase().equals(from.getCurrencyCode())) {
            throw new UnsupportedOperationException("Currency " + from.getCurrencyCode() + " is not supported as a 'from'. Only " + rates.getBase() + " is supported.");
        }

        if (!rates.getRates().containsKey(to.getCurrencyCode())) {
            throw new UnsupportedOperationException("Currency " + to.getCurrencyCode() + " is not supported as a 'to'. Only " + String.join(",", rates.getRates().keySet()) + " are supported.");
        }

        return rates.getRates().get(to.getCurrencyCode());
    }
}

