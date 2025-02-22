package com.ohrim.servlet.exchange;

import com.ohrim.dto.ExchangeRateDto;
import com.ohrim.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExchangeServlet extends ExchangeBaseServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amountParam = req.getParameter("amount");

        if (from == null || from.trim().isEmpty() ||
                to == null || to.trim().isEmpty() ||
                amountParam == null || amountParam.trim().isEmpty()) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(amountParam);
            ExchangeRateDto result = exchangeRateService.exchangeCurrency(from, to, amount);


            Map<String, Object> response = new LinkedHashMap<>();

            response.put("baseCurrency", result.getBaseCurrency());
            response.put("targetCurrency", result.getTargetCurrency());
            response.put("amount", amount);
            response.put("convertedAmount", result.getRate());

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(response));
        } catch (NumberFormatException e) {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid amount format");
        } catch (NotFoundException e) {
            sendJsonError(resp, HttpServletResponse.SC_NOT_FOUND, "Exchange rate not found for the provided currencies.");
        } catch (Exception e) {
            sendJsonError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }
}
