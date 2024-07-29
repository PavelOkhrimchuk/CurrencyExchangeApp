package com.ohrim.repository;

import com.ohrim.model.Currency;
import com.ohrim.model.ExchangeRate;
import com.ohrim.util.DatabaseUtil;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {
    @Override
    @SneakyThrows
    public List<ExchangeRate> findALL() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        String query = "SELECT er.id, er.rate, " +
                "bc.id as base_currency_id, bc.code as base_currency_code, bc.fullName as base_currency_fullName, bc.sign as base_currency_sign, " +
                "tc.id as target_currency_id, tc.code as target_currency_code, tc.fullName as target_currency_fullName, tc.sign as target_currency_sign " +
                "FROM ExchangeRates er " +
                "JOIN Currencies bc ON er.basecurrencyid= bc.id " +
                "JOIN Currencies tc ON er.targetcurrencyid = tc.id";

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRate(resultSet));
            }
        }
        return exchangeRates;
    }


    @Override
    @SneakyThrows
    public Optional<ExchangeRate> findById(Integer id) {
        String query = "SELECT er.id, er.rate, " +
                "bc.id as base_currency_id, bc.code as base_currency_code, bc.fullName as base_currency_fullName, bc.sign as base_currency_sign, " +
                "tc.id as target_currency_id, tc.code as target_currency_code, tc.fullName as target_currency_fullName, tc.sign as target_currency_sign " +
                "FROM ExchangeRates er " +
                "JOIN Currencies bc ON er.basecurrencyid = bc.id " +
                "JOIN Currencies tc ON er.targetcurrencyid = tc.id " +
                "WHERE er.id = ?";
        ExchangeRate exchangeRate = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exchangeRate = buildExchangeRate(resultSet);
                }
            }
        }
        return Optional.ofNullable(exchangeRate);
    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        String query = "DELETE FROM exchangerates WHERE ID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    @Override
    @SneakyThrows
    public void update(ExchangeRate exchangeRate) {

        String query = "UPDATE ExchangeRates SET rate = ?, base_currency_id = ?, target_currency_id = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setInt(2, exchangeRate.getBaseCurrency().getId());
            statement.setInt(3, exchangeRate.getTargetCurrency().getId());
            statement.setInt(4, exchangeRate.getId());
            statement.executeUpdate();
        }

    }

    @Override
    @SneakyThrows
    public ExchangeRate save(ExchangeRate exchangeRate) {

        String query = "INSERT INTO ExchangeRates (rate, base_currency_id, target_currency_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setInt(2, exchangeRate.getBaseCurrency().getId());
            statement.setInt(3, exchangeRate.getTargetCurrency().getId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exchangeRate.setId(generatedKeys.getInt(1));
                }
            }
        }
        return exchangeRate;

    }

    private ExchangeRate buildExchangeRate(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getInt("id"),
                new Currency(
                        resultSet.getInt("base_currency_id"),
                        resultSet.getString("base_currency_code"),
                        resultSet.getString("base_currency_fullName"),
                        resultSet.getString("base_currency_sign")
                ),
                new Currency(
                        resultSet.getInt("target_currency_id"),
                        resultSet.getString("target_currency_code"),
                        resultSet.getString("target_currency_fullName"),
                        resultSet.getString("target_currency_sign")
                ),
                resultSet.getBigDecimal("rate")
        );
    }
}
