package com.ohrim.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BaseConfiguration extends HttpServlet {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected void sendJsonError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        resp.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
