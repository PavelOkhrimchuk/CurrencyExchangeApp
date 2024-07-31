package com.ohrim.servlet.exchange;

import com.ohrim.dto.CurrencyDto;
import com.ohrim.dto.ExchangeRateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class AddExchangeRateServlet extends ExchangeBaseServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rateParam = req.getParameter("rate");

        if (baseCurrencyCode == null || baseCurrencyCode.trim().isEmpty() ||
                targetCurrencyCode == null || targetCurrencyCode.trim().isEmpty() ||
                rateParam == null || rateParam.trim().isEmpty()) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing required form fields");
            return;
        }

        try {
            BigDecimal rate = new BigDecimal(rateParam);


            CurrencyDto baseCurrencyDto = currencyService.getCurrencyByCode(baseCurrencyCode);
            CurrencyDto targetCurrencyDto = currencyService.getCurrencyByCode(targetCurrencyCode);


            if (baseCurrencyDto == null || targetCurrencyDto == null) {
                sendJsonError(resp, HttpServletResponse.SC_NOT_FOUND, "One or both currencies not found");
                return;
            }


            ExchangeRateDto existingRate = exchangeRateService.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
            if (existingRate != null) {
                sendJsonError(resp, HttpServletResponse.SC_CONFLICT, "Exchange rate already exists");
                return;
            }


            ExchangeRateDto newExchangeRate = new ExchangeRateDto(null, baseCurrencyDto, targetCurrencyDto, rate);
            ExchangeRateDto addedExchangeRate = exchangeRateService.createExchangeRate(newExchangeRate);


            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(addedExchangeRate));

        } catch (NumberFormatException e) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid rate format");
        } catch (Exception e) {
            sendJsonError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }

    }
}
