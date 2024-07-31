package com.ohrim.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {


    private static final HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();
        try {
            PropertiesUtil.loadProperties("hikari.properties");
            config.setJdbcUrl(PropertiesUtil.get("jdbcUrl"));
            config.setUsername(PropertiesUtil.get("username"));
            config.setPassword(PropertiesUtil.get("password"));
            config.setDriverClassName(PropertiesUtil.get("driverClassName"));
            config.addDataSourceProperty("cachePrepStmts", PropertiesUtil.get("cachePrepStmts"));
            config.addDataSourceProperty("prepStmtCacheSize", PropertiesUtil.get("prepStmtCacheSize"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", PropertiesUtil.get("prepStmtCacheSqlLimit"));

            ds = new HikariDataSource(config);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to load database properties", ex);
        }
    }

    private DatabaseUtil() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
