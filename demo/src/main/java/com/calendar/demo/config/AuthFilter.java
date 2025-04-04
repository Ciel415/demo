package com.calendar.demo.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (path.startsWith("/api/auth") || path.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

        if (req.getSession(false) == null || req.getSession().getAttribute("userId") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            res.getWriter().write("로그인이 필요합니다.");
            return;
        }

        chain.doFilter(request, response);
    }
}
