package com.ohrim.service;

import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.exception.NotFoundException;
import com.ohrim.mapper.DtoConverter;
import com.ohrim.model.ExchangeRate;
import com.ohrim.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
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

        Optional<ExchangeRateDto> directRateDto = findDirectRate(from, to, amount);
        if (directRateDto.isPresent()) {
            return directRateDto.get();
        }


        Optional<ExchangeRateDto> reverseRateDto = findReverseRate(from, to, amount);
        if (reverseRateDto.isPresent()) {
            return reverseRateDto.get();
        }


        Optional<ExchangeRateDto> crossRateDto = findCrossRate(from, to, amount);
        if (crossRateDto.isPresent()) {
            return crossRateDto.get();
        }

        throw new NotFoundException("Exchange rate not found for the provided currencies.");
    }

    private Optional<ExchangeRateDto> findDirectRate(String from, String to, BigDecimal amount) {
        Optional<ExchangeRate> directRate = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(from, to);
        if (directRate.isPresent()) {
            BigDecimal rate = directRate.get().getRate();
            BigDecimal exchangedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            return Optional.of(ExchangeRateDto.builder()
                    .baseCurrency(DtoConverter.toCurrencyDto(directRate.get().getBaseCurrency()))
                    .targetCurrency(DtoConverter.toCurrencyDto(directRate.get().getTargetCurrency()))
                    .rate(exchangedAmount)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<ExchangeRateDto> findReverseRate(String from, String to, BigDecimal amount) {
        Optional<ExchangeRate> reverseRate = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(to, from);
        if (reverseRate.isPresent()) {
            BigDecimal rate = BigDecimal.ONE.divide(reverseRate.get().getRate(), 4, RoundingMode.HALF_UP);
            BigDecimal exchangedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            return Optional.of(ExchangeRateDto.builder()
                    .baseCurrency(DtoConverter.toCurrencyDto(reverseRate.get().getBaseCurrency()))
                    .targetCurrency(DtoConverter.toCurrencyDto(reverseRate.get().getTargetCurrency()))
                    .rate(exchangedAmount)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<ExchangeRateDto> findCrossRate(String from, String to, BigDecimal amount) {
        List<ExchangeRate> allRates = exchangeRateRepository.findALL();
        for (ExchangeRate intermediateRate : allRates) {
            if (intermediateRate.getBaseCurrency().getCode().equals(from)) {
                Optional<ExchangeRate> crossRates = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                        intermediateRate.getBaseCurrency().getCode(), to);
                if (crossRates.isPresent()) {
                    BigDecimal rate = intermediateRate.getRate().multiply(crossRates.get().getRate());
                    BigDecimal exchangedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
                    return Optional.of(ExchangeRateDto.builder()
                            .baseCurrency(DtoConverter.toCurrencyDto(intermediateRate.getBaseCurrency()))
                            .targetCurrency(DtoConverter.toCurrencyDto(crossRates.get().getTargetCurrency()))
                            .rate(exchangedAmount)
                            .build());
                }
            }
        }
        return Optional.empty();
    }
}