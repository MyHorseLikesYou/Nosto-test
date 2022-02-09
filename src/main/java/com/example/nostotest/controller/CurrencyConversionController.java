package com.example.nostotest.controller;

import com.example.nostotest.dto.CurrencyConversionResultDto;
import com.example.nostotest.model.Money;
import com.example.nostotest.service.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;


@RequestMapping("/api/v1")
@RestController
public class CurrencyConversionController {

    private CurrencyConverter currencyConverter;

    @Autowired
    public CurrencyConversionController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    @GetMapping("/convert")
    public CurrencyConversionResultDto greeting(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "amount") double amount) throws IOException {

        if (from == null || from.isEmpty())
            throw new IllegalArgumentException("'from' is required");

        if (to == null || to.isEmpty())
            throw new IllegalArgumentException("'to' is required");

        if (amount < 0)
            throw new IllegalArgumentException("'amount' can't be less than 0");

        var sourceCurrency = TryCreateCurrency(from);
        var sourceMoney = new Money(new BigDecimal(amount), sourceCurrency);

        var targetCurrency = TryCreateCurrency(to);
        var targetMoney = currencyConverter.Convert(sourceMoney, targetCurrency);

        return new CurrencyConversionResultDto(targetMoney.getValue());
    }

    private Currency TryCreateCurrency(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal currency code " + currencyCode);
        }
    }
}

