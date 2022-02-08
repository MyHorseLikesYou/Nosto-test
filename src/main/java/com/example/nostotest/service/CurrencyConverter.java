package com.example.nostotest.service;

import com.example.nostotest.model.Money;

import java.io.IOException;
import java.util.Currency;

public interface CurrencyConverter {
    Money Convert(Money amount, Currency targetCurrency) throws IOException;
}


