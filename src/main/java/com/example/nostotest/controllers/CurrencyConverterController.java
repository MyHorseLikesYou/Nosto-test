package com.example.nostotest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConverterController {
    @GetMapping("/convert")
    public CurrencyConversionResult greeting(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "amount") Float amount) {
        return new CurrencyConversionResult(0);
    }
}

