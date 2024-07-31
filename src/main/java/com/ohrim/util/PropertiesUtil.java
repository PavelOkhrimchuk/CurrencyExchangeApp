package com.ohrim.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            loadProperties("hikari.properties");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file.", e);
        }
    }

    static void loadProperties(String fileName) throws IOException {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
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
