package com.ohrim.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohrim.dto.CurrencyDto;
import com.ohrim.repository.CurrencyRepository;
import com.ohrim.repository.CurrencyRepositoryImpl;
import com.ohrim.service.CurrencyService;
import com.ohrim.service.CurrencyServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class CurrencyServlet extends HttpServlet {
    private CurrencyService currencyService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        try {
            CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();
            this.currencyService = new CurrencyServiceImpl(currencyRepository);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize CurrencyServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CurrencyDto> currencies = currencyService.getAllCurrencies();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(objectMapper.writeValueAsString(currencies));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();  // Логирование ошибки
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve currencies: " + e.getMessage());
        }
    }

}
