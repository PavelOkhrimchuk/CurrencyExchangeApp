package com.ohrim.servlet.exchange;

import com.ohrim.repository.ExchangeRateRepository;
import com.ohrim.repository.ExchangeRateRepositoryImpl;
import com.ohrim.service.ExchangeRateService;
import com.ohrim.service.ExchangeRateServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class ExchangeBaseServlet extends HttpServlet {

    protected ExchangeRateService exchangeRateService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryImpl();
            this.exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize servlet", e);
        }
    }
}
