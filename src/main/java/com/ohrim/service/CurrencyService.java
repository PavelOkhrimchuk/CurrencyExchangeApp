package com.ohrim.service;

import com.ohrim.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> getAllCurrencies();
    CurrencyDto getCurrencyByCode(String code);
    CurrencyDto createCurrency(CurrencyDto currencyDto);
    void updateCurrency(CurrencyDto currencyDto);
}
