package com.ohrim.servlet.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohrim.dto.CurrencyDto;
import com.ohrim.exception.ConflictException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddCurrencyServlet extends BaseServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");


        if (fullName == null || fullName.trim().isEmpty() ||
                code == null || code.trim().isEmpty() ||
                sign == null || sign.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required form fields");
            return;
        }

        try {
            CurrencyDto newCurrency = new CurrencyDto(null, fullName, code, sign);
            CurrencyDto addedCurrency = currencyService.createCurrency(newCurrency);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(addedCurrency));
        } catch (ConflictException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Currency with this code already exists");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

}

