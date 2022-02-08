package com.example.nostotest.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
    private BigDecimal value;
    private Currency currency;

    public Money(double value, Currency currency) {
        this(new BigDecimal(value), currency);
    }

    public Money(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean hasCurrency(Currency currency) {
        return this.currency.getCurrencyCode().equals(currency.getCurrencyCode());
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Money) {
            Money money = (Money) obj;
            return this.hasCurrency(money.getCurrency()) && value.equals(money.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%0$.2f %s", value, getCurrency().getSymbol());
    }
}
