package com.ohrim.servlet.exchange;

import com.ohrim.dto.ExchangeRateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GetExchangeRatesServlet extends ExchangeBaseServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<ExchangeRateDto> exchangeRates = exchangeRateService.getAllExchangeRates();
            resp.getWriter().write(objectMapper.writeValueAsString(exchangeRates));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
           sendJsonError(resp,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve currencies: " + e.getMessage());
        }

    }
}
