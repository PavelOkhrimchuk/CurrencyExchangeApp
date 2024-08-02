package com.ohrim.servlet.exchange;

import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.exception.NotFoundException;
import com.ohrim.util.RequestUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.RoundingMode;

public class ReceiveExchangeRateServlet extends ExchangeBaseServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = RequestUtil.getPathInfo(req);

        if (!RequestUtil.isValidCurrencyPair(pathInfo)) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Currency pair is missing or too short in the URL");
            return;
        }


        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3);

        try {
            ExchangeRateDto exchangeRate = exchangeRateService.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate != null) {
                exchangeRate.setRate(exchangeRate.getRate().setScale(2, RoundingMode.HALF_UP));
                resp.getWriter().write(objectMapper.writeValueAsString(exchangeRate));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                sendJsonError(resp,HttpServletResponse.SC_NOT_FOUND, "Exchange rate not found for the given currency pair");
            }
        } catch (NotFoundException e) {
           sendJsonError(resp,HttpServletResponse.SC_NOT_FOUND, "Exchange rate not found for the given currency pair");
        } catch (Exception e) {
           sendJsonError(resp,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve exchange rate: " + e.getMessage());
        }

    }
}
