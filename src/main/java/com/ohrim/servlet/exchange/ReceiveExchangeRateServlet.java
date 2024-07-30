package com.ohrim.servlet.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReceiveExchangeRateServlet extends ExchangeBaseServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo() != null ? req.getPathInfo().substring(1) : null;
        if (pathInfo == null || pathInfo.length() < 6) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Currency pair is missing or too short in the URL");
            return;
        }

        if (pathInfo.length() != 6) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Currency pair is incorrect");
            return;
        }

        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3);
        try {
            ExchangeRateDto exchangeRate = exchangeRateService.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(objectMapper.writeValueAsString(exchangeRate));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (NotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Exchange rate not found for the given currency pair");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve exchange rate: " + e.getMessage());
        }

    }
}
