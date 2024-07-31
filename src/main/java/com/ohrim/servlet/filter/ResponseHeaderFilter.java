package com.ohrim.servlet.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class ResponseHeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
