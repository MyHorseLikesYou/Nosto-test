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
    public double getRate(Currency source, Currency target) throws IOException {
        GetLatestCurrencyRatesResponse rates = client.getLatest();

        if (!rates.getBase().equals(source.getCurrencyCode())) {
            throw new UnsupportedOperationException("Currency " + source.getCurrencyCode() + " is not supported as a source");
        }

        if (!rates.getRates().containsKey(target.getCurrencyCode())) {
            throw new UnsupportedOperationException("Currency " + target.getCurrencyCode() + " is not supported as a target");
        }

        return rates.getRates().get(target.getCurrencyCode());
    }
}

