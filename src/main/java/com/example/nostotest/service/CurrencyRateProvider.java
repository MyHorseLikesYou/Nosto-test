package com.example.nostotest.service;

import java.io.IOException;
import java.util.Currency;

public interface CurrencyRateProvider {
    double getRate(Currency source, Currency target) throws IOException;
}

