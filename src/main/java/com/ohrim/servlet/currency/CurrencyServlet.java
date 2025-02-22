package com.ohrim.servlet.currency;


import com.ohrim.dto.CurrencyDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class CurrencyServlet extends CurrencyBaseServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CurrencyDto> currencies = currencyService.getAllCurrencies();
            resp.getWriter().write(objectMapper.writeValueAsString(currencies));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            sendJsonError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve currencies: " + e.getMessage());
        }
    }


}
