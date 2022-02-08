package com.example.nostotest.controller;

import com.example.nostotest.dto.CurrencyConversionResultDto;
import com.example.nostotest.model.Money;
import com.example.nostotest.service.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;


@RestController
public class CurrencyConversionController {

    private CurrencyConverter currencyConverter;

    @Autowired
    public CurrencyConversionController(CurrencyConverter currencyConverter)
    {
        this.currencyConverter = currencyConverter;
    }

    @GetMapping("/convert")
    public CurrencyConversionResultDto greeting(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "amount") double amount) throws IOException {

        var sourceCurrency = Currency.getInstance(from);
        var sourceMoney = new Money(new BigDecimal(amount), sourceCurrency);

        var targetCurrency = Currency.getInstance(to);
        var targetMoney = currencyConverter.Convert(sourceMoney, targetCurrency);

        return new CurrencyConversionResultDto(targetMoney.getValue());
    }
}

