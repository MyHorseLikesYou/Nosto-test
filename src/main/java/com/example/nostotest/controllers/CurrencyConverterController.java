package com.example.nostotest.controllers;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;


@RestController
public class CurrencyConverterController {
    @GetMapping("/convert")
    public CurrencyConversionResult greeting(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "amount") Float amount) throws IOException {

        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://api.exchangeratesapi.io/v1/latest?access_key=fdb0c2b963512318dd69f1b85d69d35f&symbols=USD,AUD,CAD,PLN,MXN&format=1")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            ExternalResult entity = gson.fromJson(body, ExternalResult.class);

            return new CurrencyConversionResult(entity.getRates().entrySet().iterator().next().getValue() * amount);
        }
    }
}

class ExternalResult {
    private final String base;
    private final HashMap<String, Float> rates;

    public ExternalResult(String base, HashMap<String, Float> rates) {
        this.base = base;
        this.rates = rates;
    }

    public HashMap<String, Float> getRates() {
        return rates;
    }

    public String getBase() {
        return base;
    }
}

