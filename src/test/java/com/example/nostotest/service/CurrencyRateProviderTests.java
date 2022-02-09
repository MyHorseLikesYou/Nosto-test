package com.example.nostotest.service;

import com.example.nostotest.model.GetLatestCurrencyRatesResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateProviderTests {

    @Mock
    private ExchangeRatesApiClient exchangeRatesApiClient;

    @InjectMocks
    private CurrencyRateProviderImpl currencyRateProvider;

    @Test
    void givenUnknownSourceCurrency_whenGetRate_thenThrowException() throws IOException {
        GetLatestCurrencyRatesResponse latestCurrencyRatesResponse = new GetLatestCurrencyRatesResponse(
                "EUR",
                new HashMap<>() {{
                    put("RUB", 80.0);
                }});

        given(exchangeRatesApiClient.getLatest()).willReturn(latestCurrencyRatesResponse);

        Currency sourceCurrency = Currency.getInstance("USD");
        Currency targetCurrency = Currency.getInstance("RUB");

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            double rate = currencyRateProvider.getRate(sourceCurrency, targetCurrency);
        });

        assertThat(exception.getMessage(),  Matchers.comparesEqualTo("Currency USD is not supported as a source"));
    }

    @Test
    void givenUnknownTargetCurrency_whenGetRate_thenThrowException() throws IOException {
        GetLatestCurrencyRatesResponse latestCurrencyRatesResponse = new GetLatestCurrencyRatesResponse(
                "EUR",
                new HashMap<>() {{
                    put("RUB", 80.0);
                }});

        given(exchangeRatesApiClient.getLatest()).willReturn(latestCurrencyRatesResponse);

        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("USD");

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            double rate = currencyRateProvider.getRate(sourceCurrency, targetCurrency);
        });

        assertThat(exception.getMessage(),  Matchers.comparesEqualTo("Currency USD is not supported as a target"));
    }

    @Test
    void givenKnownCurrencies_whenGetRate_thenReturnCorrectRate() throws IOException {
        GetLatestCurrencyRatesResponse latestCurrencyRatesResponse = new GetLatestCurrencyRatesResponse(
                "EUR",
                new HashMap<>() {{
                    put("RUB", 80.0);
                }});

        given(exchangeRatesApiClient.getLatest()).willReturn(latestCurrencyRatesResponse);

        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("RUB");
        double rate = currencyRateProvider.getRate(sourceCurrency, targetCurrency);

        assertThat(rate,  Matchers.comparesEqualTo(80.0));
    }
}
