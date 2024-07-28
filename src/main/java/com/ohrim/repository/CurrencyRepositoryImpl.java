package com.ohrim.repository;

import com.ohrim.model.Currency;
import com.ohrim.util.DatabaseUtil;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository{

    @Override
    @SneakyThrows
    public List<Currency> findALL() {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM Currencies";

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
        }
        return currencies;

    }


    @Override
    @SneakyThrows
    public Optional<Currency> findById(Integer id) {
        String query = "SELECT * FROM currencies WHERE id = ?";
        Currency currency = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    currency = buildCurrency(resultSet);
                }
            }

        }
        return Optional.ofNullable(currency);

    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        String query = "DELETE FROM Currencies WHERE ID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }

    }

    @Override
    @SneakyThrows
    public void update(Currency currency) {
        String query = "UPDATE currencies SET code = ?, fullname = ?, sign = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());
            statement.setInt(4, currency.getId());
            statement.executeUpdate();
        }

    }

    @Override
    @SneakyThrows
    public Currency save(Currency currency) {
        String query = "INSERT INTO currencies (code, fullname, sign) VALUES (?,?,?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    currency.setId(generatedKeys.getInt(1));
                }
            }

        }

        return currency;

    }

    @Override
    @SneakyThrows
    public Optional<Currency> findByCode(String code) {
        String query = "SELECT * FROM currencies WHERE code = ?";
        Currency currency = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    currency = buildCurrency(resultSet);
                }
            }
        }
        return Optional.ofNullable(currency);
    }



    private Currency buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("code", String.class),
                resultSet.getObject("fullname", String.class),
                resultSet.getObject("sign", String.class)
        );

    }


}
