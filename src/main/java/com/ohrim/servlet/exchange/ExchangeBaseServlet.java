package com.ohrim.servlet.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohrim.repository.CurrencyRepository;
import com.ohrim.repository.CurrencyRepositoryImpl;
import com.ohrim.repository.ExchangeRateRepository;
import com.ohrim.repository.ExchangeRateRepositoryImpl;
import com.ohrim.service.CurrencyService;
import com.ohrim.service.CurrencyServiceImpl;
import com.ohrim.service.ExchangeRateService;
import com.ohrim.service.ExchangeRateServiceImpl;
import com.ohrim.servlet.ErrorResponse;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExchangeBaseServlet extends HttpServlet {

    protected ExchangeRateService exchangeRateService;

    protected CurrencyService currencyService;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected void sendJsonError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        resp.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();
            ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryImpl();

            this.currencyService = new CurrencyServiceImpl(currencyRepository);
            this.exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize servlet", e);
        }
    }
}
