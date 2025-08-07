package com.rushabh.newsaggregator.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rushabh.newsaggregator.dto.Response.ApiResponse;
import com.rushabh.newsaggregator.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ApiResponse<Object> res = ApiResponse.error("Missing Authorization Header", null);
            String jsonResponse = objectMapper.writeValueAsString(res);
            response.getWriter().write(jsonResponse);
            return;
        }

        String token = authHeader.substring(7);
        Claims payload = JwtUtils.verifyToken(token, secret);
        if (payload == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ApiResponse<Object> res = ApiResponse.error("Invalid token!", null);
            String jsonResponse = objectMapper.writeValueAsString(res);
            response.getWriter().write(jsonResponse);
            return;
        }

        String userName = payload.getSubject();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }
}
