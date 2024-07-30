package com.ohrim.servlet.currency;

import com.ohrim.repository.CurrencyRepository;
import com.ohrim.repository.CurrencyRepositoryImpl;
import com.ohrim.service.CurrencyService;
import com.ohrim.service.CurrencyServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {

    protected CurrencyService currencyService;

    @Override
    public void init() throws ServletException {

        try {
            CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();
            this.currencyService = new CurrencyServiceImpl(currencyRepository);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize servlet", e);
        }

    }


}
