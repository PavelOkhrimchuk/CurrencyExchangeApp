package com.ohrim.repository;

import com.ohrim.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateRepository extends Repository<Integer, ExchangeRate> {
    Optional<ExchangeRate> findByBaseCurrencyCodeAndTargetCurrencyCode(String baseCurrencyCode, String targetCurrencyCode);
}
