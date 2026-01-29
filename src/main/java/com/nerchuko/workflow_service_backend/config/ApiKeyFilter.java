package com.nerchuko.workflow_service_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter  {

    private static final String API_KEY_HEADER = "X-API-Key";

    @Value("${app.security.api-key}")
    private String validApiKey;

//    private static final String VALID_API_KEY = "your-super-secret-api-key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        //  Only protect /api/events (and its subpaths)
        if (!path.startsWith("/api/events")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);

        if( apiKey == null || !apiKey.equals(validApiKey)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid or missing Api Key");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
