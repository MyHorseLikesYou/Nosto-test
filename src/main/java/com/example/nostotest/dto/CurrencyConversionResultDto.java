package com.example.nostotest.dto;

import java.math.BigDecimal;

public class CurrencyConversionResultDto {
    private final BigDecimal result;

    public CurrencyConversionResultDto(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
}
