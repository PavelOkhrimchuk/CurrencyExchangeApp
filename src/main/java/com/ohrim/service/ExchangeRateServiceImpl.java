package com.ohrim.service;

import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.exception.NotFoundException;
import com.ohrim.mapper.DtoConverter;
import com.ohrim.model.Currency;
import com.ohrim.model.ExchangeRate;
import com.ohrim.repository.CurrencyRepository;
import com.ohrim.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(CurrencyRepository currencyRepository, ExchangeRateRepository exchangeRateRepository) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRateDto> getAllExchangeRates() {
        return exchangeRateRepository.findALL().stream()
                .map(DtoConverter::toExchangeRateDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExchangeRateDto getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        return exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(baseCurrencyCode, targetCurrencyCode)
                .map(DtoConverter::toExchangeRateDto)
                .orElseThrow(() -> new NotFoundException("Exchange rate not found"));
    }

    @Override
    public ExchangeRateDto createExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = DtoConverter.toExchangeRateEntity(exchangeRateDto);
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRate);
        return DtoConverter.toExchangeRateDto(savedExchangeRate);
    }

    @Override
    public ExchangeRateDto updateExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = DtoConverter.toExchangeRateEntity(exchangeRateDto);
        exchangeRateRepository.update(exchangeRate);
        return DtoConverter.toExchangeRateDto(exchangeRate);

    }

    @Override
    public ExchangeRateDto exchangeCurrency(String from, String to, BigDecimal amount) {
        Currency baseCurrency = currencyRepository.findByCode(from)
                .orElseThrow(() -> new NotFoundException("Base currency not found"));
        Currency targetCurrency = currencyRepository.findByCode(to)
                .orElseThrow(() -> new NotFoundException("Target currency not found"));

        Optional<ExchangeRate> directRate = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(from, to);
        if (directRate.isPresent()) {
            BigDecimal rate = directRate.get().getRate();
            BigDecimal exchangedAmount = amount.multiply(rate);
            return ExchangeRateDto.builder()
                    .baseCurrency(DtoConverter.toCurrencyDto(baseCurrency))
                    .targetCurrency(DtoConverter.toCurrencyDto(targetCurrency))
                    .rate(exchangedAmount)
                    .build();
        }

        Optional<ExchangeRate> reverseRate = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(to, from);
        if (reverseRate.isPresent()) {
            BigDecimal rate = BigDecimal.ONE.divide(reverseRate.get().getRate(), 4, RoundingMode.HALF_UP);
            BigDecimal exchangedAmount = amount.multiply(rate);
            exchangedAmount = exchangedAmount.setScale(2, RoundingMode.HALF_UP);
            return ExchangeRateDto.builder()
                    .baseCurrency(DtoConverter.toCurrencyDto(baseCurrency))
                    .targetCurrency(DtoConverter.toCurrencyDto(targetCurrency))
                    .rate(exchangedAmount)
                    .build();


        }
        List<ExchangeRate> allRates = exchangeRateRepository.findALL();
        for (ExchangeRate intermediateRate : allRates) {
            if (intermediateRate.getBaseCurrency().getCode().equals(from)) {
                Optional<ExchangeRate> crossRates = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                        intermediateRate.getBaseCurrency().getCode(),
                        to);
                if (crossRates.isPresent()) {
                    BigDecimal rate = intermediateRate.getRate().multiply(crossRates.get().getRate());
                    BigDecimal exchangedAmount = amount.multiply(rate);
                    return ExchangeRateDto.builder()
                            .baseCurrency(DtoConverter.toCurrencyDto(baseCurrency))
                            .targetCurrency(DtoConverter.toCurrencyDto(targetCurrency))
                            .rate(exchangedAmount)
                            .build();
                }

            }
        }

        throw new NotFoundException("Exchange rate not found for the given currencies");

    }
}
