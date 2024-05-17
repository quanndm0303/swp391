package com.app.zware.Filters;

import com.app.zware.Util.JwtUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @Nonnull  HttpServletResponse response,
            @Nonnull  FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/auth/register") || requestURI.equals("/api/auth/login")) {
            // Bypass JWT authentication for /api/register
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }

        if (!JwtUtil.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
