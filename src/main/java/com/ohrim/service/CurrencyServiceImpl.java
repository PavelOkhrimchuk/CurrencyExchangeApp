package com.ohrim.service;

import com.ohrim.dto.CurrencyDto;
import com.ohrim.exception.ConflictException;
import com.ohrim.mapper.DtoConverter;
import com.ohrim.model.Currency;
import com.ohrim.repository.CurrencyRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    @Override
    public List<CurrencyDto> getAllCurrencies() {
        return currencyRepository.findALL().stream()
                .map(DtoConverter::toCurrencyDto)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyDto getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code)
                .map(DtoConverter::toCurrencyDto).orElse(null);
    }

    @Override
    public CurrencyDto createCurrency(CurrencyDto currencyDto) {
        if (currencyRepository.findByCode(currencyDto.getCode()).isPresent()) {
            throw new ConflictException("Currency with code " + currencyDto.getCode() + " already exists.");
        }
        Currency currency = DtoConverter.toCurrencyEntity(currencyDto);
        Currency savedCurrency = currencyRepository.save(currency);
        return DtoConverter.toCurrencyDto(savedCurrency);
    }

    @Override
    public CurrencyDto updateCurrency(CurrencyDto currencyDto) {
        Currency currency = DtoConverter.toCurrencyEntity(currencyDto);
        currencyRepository.update(currency);
        return DtoConverter.toCurrencyDto(currency);

    }
}
