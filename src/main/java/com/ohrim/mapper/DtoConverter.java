package com.ohrim.mapper;

import com.ohrim.dto.CurrencyDto;
import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.model.Currency;
import com.ohrim.model.ExchangeRate;
import org.modelmapper.ModelMapper;

public class DtoConverter {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static CurrencyDto toCurrencyDto(Currency currency) {
        return modelMapper.map(currency, CurrencyDto.class);
    }

    public static Currency toCurrencyEntity(CurrencyDto currencyDto) {
        return modelMapper.map(currencyDto, Currency.class);
    }

    public static ExchangeRateDto toExchangeRateDto(ExchangeRate exchangeRate) {
        return modelMapper.map(exchangeRate, ExchangeRateDto.class);
    }

    public static ExchangeRate toExchangeRateEntity(ExchangeRateDto exchangeRateDto) {
        return modelMapper.map(exchangeRateDto, ExchangeRate.class);
    }


}
