package com.ohrim.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getPathInfo(HttpServletRequest req) {
        return req.getPathInfo() != null ? req.getPathInfo().substring(1) : null;
    }

    public static boolean isValidCurrencyPair(String pathInfo) {
        return pathInfo != null && pathInfo.length() == 6;
    }


}
