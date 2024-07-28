package com.ohrim.repository;

import com.ohrim.model.Currency;

import java.util.Optional;

public interface CurrencyRepository extends Repository<Integer, Currency> {
    Optional<Currency> findByCode(String code);

}
