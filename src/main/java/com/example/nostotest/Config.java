package com.example.nostotest;

import com.example.nostotest.service.CurrencyConverter;
import com.example.nostotest.service.CurrencyConverterImpl;
import com.example.nostotest.service.CurrencyRateProvider;
import com.example.nostotest.service.CurrencyRateProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.nostotest")
public class Config {
    @Bean
    public CurrencyRateProvider currencyRateProvider() {
        return new CurrencyRateProviderImpl("http://api.exchangeratesapi.io/v1/", "fdb0c2b963512318dd69f1b85d69d35f");
    }

    @Bean
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverterImpl(currencyRateProvider());
    }
}
