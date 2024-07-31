package com.ohrim.servlet.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohrim.repository.CurrencyRepository;
import com.ohrim.repository.CurrencyRepositoryImpl;
import com.ohrim.service.CurrencyService;
import com.ohrim.service.CurrencyServiceImpl;
import com.ohrim.servlet.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    protected CurrencyService currencyService;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected void sendJsonError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        resp.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

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
