package com.ohrim.servlet.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohrim.dto.CurrencyDto;
import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

public class UpdateExchangeRateServlet extends ExchangeBaseServlet{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    private void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo() != null ? req.getPathInfo().substring(1) : null;

        if (pathInfo == null || pathInfo.length() != 6) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Currency pair is missing or incorrect in the URL");
            return;
        }

        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3);

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String requestBody = stringBuilder.toString();

        String rateParam = null;

        String[] params = requestBody.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals("rate")) {
                rateParam = keyValue[1];
                break;
            }
        }

        if (rateParam == null || rateParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required form field: rate");
            return;
        }

        try {
            BigDecimal rate = new BigDecimal(rateParam);

            CurrencyDto baseCurrencyDto = currencyService.getCurrencyByCode(baseCurrencyCode);
            CurrencyDto targetCurrencyDto = currencyService.getCurrencyByCode(targetCurrencyCode);

            if (baseCurrencyDto == null || targetCurrencyDto == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Currency pair not found");
                return;
            }

            ExchangeRateDto existingRate = exchangeRateService.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
            ExchangeRateDto updatedExchangeRate = new ExchangeRateDto(existingRate.getId(), baseCurrencyDto, targetCurrencyDto, rate);
            ExchangeRateDto result = exchangeRateService.updateExchangeRate(updatedExchangeRate);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(result));

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid rate format");
        } catch (NotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Exchange rate not found for the given currency pair");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }
}
