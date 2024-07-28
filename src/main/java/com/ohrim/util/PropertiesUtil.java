package com.ohrim.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file.", e);
        }
    }

    private static void loadProperties() throws IOException {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            } else {
                throw new IOException("Properties file not found.");
            }
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

}
