package com.ohrim.servlet.currency;

import com.ohrim.dto.CurrencyDto;
import com.ohrim.exception.ConflictException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddCurrencyServlet extends BaseServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");


        if (fullName == null || fullName.trim().isEmpty() ||
                code == null || code.trim().isEmpty() ||
                sign == null || sign.trim().isEmpty()) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing required form fields");
            return;
        }

        try {
            CurrencyDto newCurrency = new CurrencyDto(null, fullName, code, sign);
            CurrencyDto addedCurrency = currencyService.createCurrency(newCurrency);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(addedCurrency));
        } catch (ConflictException e) {
            sendJsonError(resp, HttpServletResponse.SC_CONFLICT, "Currency with this code already exists");
        } catch (Exception e) {
            sendJsonError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

}

