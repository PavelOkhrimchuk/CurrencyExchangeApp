package com.ohrim.service;

import com.ohrim.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateServiceImpl implements ExchangeRateService{
    @Override
    public List<ExchangeRateDto> getAllExchangeRates() {
        return null;
    }

    @Override
    public ExchangeRateDto getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        return null;
    }

    @Override
    public ExchangeRateDto createExchangeRate(ExchangeRateDto exchangeRateDto) {
        return null;
    }

    @Override
    public ExchangeRateDto updateExchangeRate(ExchangeRateDto exchangeRateDto) {
        return null;
    }

    @Override
    public ExchangeRateDto exchangeCurrency(String from, String to, BigDecimal amount) {
        return null;
    }
}
