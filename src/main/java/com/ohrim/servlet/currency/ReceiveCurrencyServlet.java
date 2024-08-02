package com.ohrim.servlet.currency;

import com.ohrim.dto.CurrencyDto;
import com.ohrim.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReceiveCurrencyServlet extends CurrencyBaseServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyCode = req.getPathInfo() != null ? req.getPathInfo().substring(1) : null;
        if (currencyCode == null || currencyCode.isEmpty()) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Currency code is missing from the URL");
            return;
        }

        try {
            CurrencyDto currency = currencyService.getCurrencyByCode(currencyCode);
            if (currency != null) {
                resp.getWriter().write(objectMapper.writeValueAsString(currency));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                sendJsonError(resp, HttpServletResponse.SC_NOT_FOUND, "Currency not found");
            }
        } catch (NotFoundException e) {
          sendJsonError(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            sendJsonError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }
}
