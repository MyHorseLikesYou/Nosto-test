package com.example.nostotest.service;

import com.example.nostotest.model.Money;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyConverterImpl implements CurrencyConverter {

    private CurrencyRateProvider currencyRatesProvider;

    public CurrencyConverterImpl(CurrencyRateProvider currencyRateProvider)
    {
        this.currencyRatesProvider = currencyRateProvider;
    }

    @Override
    public Money Convert(Money amount, Currency targetCurrency) throws IOException {

        if (amount.hasCurrency(targetCurrency))
            return amount;

        var rate = currencyRatesProvider.getRate(amount.getCurrency(), targetCurrency);
        return new Money(amount.getValue().multiply(new BigDecimal(rate)), targetCurrency);
    }
}
