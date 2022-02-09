package com.example.nostotest.service;

import com.example.nostotest.model.GetLatestCurrencyRatesResponse;

import java.io.IOException;

public interface ExchangeRatesApiClient {
    GetLatestCurrencyRatesResponse getLatest() throws IOException;
}



