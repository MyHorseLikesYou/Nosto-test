package com.example.nostotest;

import com.example.nostotest.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.nostotest")
public class Config {

    public ExchangeRatesApiClient exchangeRatesApiClient() {
        return new ExchangeRatesApiClientImpl("http://api.exchangeratesapi.io/v1/", "fdb0c2b963512318dd69f1b85d69d35f");
    }

    @Bean
    public CurrencyRateProvider currencyRateProvider() {
        return new CurrencyRateProviderImpl(exchangeRatesApiClient());
    }

    @Bean
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverterImpl(currencyRateProvider());
    }
}
