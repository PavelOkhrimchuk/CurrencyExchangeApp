package com.ohrim.repository;

import com.ohrim.model.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepository{
    @Override
    public List<ExchangeRate> findALL() {
        return null;
    }

    @Override
    public Optional<ExchangeRate> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(ExchangeRate entity) {

    }

    @Override
    public ExchangeRate save(ExchangeRate entity) {
        return null;
    }
}
