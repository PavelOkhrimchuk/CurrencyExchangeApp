package com.ohrim.service;

import com.ohrim.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRateDto> getAllExchangeRates();
    ExchangeRateDto getExchangeRate(String baseCurrencyCode, String targetCurrencyCode);
    ExchangeRateDto createExchangeRate(ExchangeRateDto exchangeRateDto);
    ExchangeRateDto updateExchangeRate(ExchangeRateDto exchangeRateDto);
    ExchangeRateDto exchangeCurrency(String from, String to, BigDecimal amount);
}
