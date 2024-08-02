package com.ohrim.servlet.exchange;

import com.ohrim.repository.CurrencyRepository;
import com.ohrim.repository.CurrencyRepositoryImpl;
import com.ohrim.repository.ExchangeRateRepository;
import com.ohrim.repository.ExchangeRateRepositoryImpl;
import com.ohrim.service.CurrencyService;
import com.ohrim.service.CurrencyServiceImpl;
import com.ohrim.service.ExchangeRateService;
import com.ohrim.service.ExchangeRateServiceImpl;
import com.ohrim.servlet.BaseConfiguration;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

public class ExchangeBaseServlet extends BaseConfiguration {

    protected ExchangeRateService exchangeRateService;

    protected CurrencyService currencyService;


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
