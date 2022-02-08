package com.example.nostotest.service;

import com.example.nostotest.model.Money;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterTests {

    @Mock
    private CurrencyRateProvider currencyRateProvider;

    @InjectMocks
    private CurrencyConverterImpl currencyConverter;

    @Test
    void givenSameCurrencies_whenConvert_thenReturnSameAmount() throws IOException {

        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("EUR");

        Money sourceMoneyAmount = new Money(1, sourceCurrency);
        Money resultMoneyAmount = currencyConverter.Convert(sourceMoneyAmount, targetCurrency);

        assertThat(resultMoneyAmount.getValue(),  Matchers.comparesEqualTo(sourceMoneyAmount.getValue()));
    }

    @Test
    void givenAmountIsZero_whenConvert_thenReturnZero() throws IOException {

        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("RUB");

        Money sourceMoneyAmount = new Money(0, sourceCurrency);
        Money resultMoneyAmount = currencyConverter.Convert(sourceMoneyAmount, targetCurrency);

        assertThat(resultMoneyAmount.getValue(),  Matchers.comparesEqualTo(sourceMoneyAmount.getValue()));
    }

    @Test
    void givenAmountGreaterThanZeroAndDifferentCurrencies_whenCalculateArea_thenReturnConvertedValue() throws IOException {

        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("RUB");

        given(currencyRateProvider.getRate(sourceCurrency, targetCurrency)).willReturn(80.0);

        Money sourceMoneyAmount = new Money(1.0, sourceCurrency);
        Money resultMoneyAmount = currencyConverter.Convert(sourceMoneyAmount, targetCurrency);

        assertThat(resultMoneyAmount.getValue(),  Matchers.comparesEqualTo(new BigDecimal(80)));
    }
}
