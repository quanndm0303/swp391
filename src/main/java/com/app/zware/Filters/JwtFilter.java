package com.app.zware.Filters;

import com.app.zware.Service.UserService;
import com.app.zware.Util.JwtUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {


  private final UserService userService;

  public JwtFilter(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain) throws ServletException, IOException {

    //Allow Access-Control
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
    response.setHeader("Access-Control-Allow-Credentials", "true");

    // Bypass JWT authentication for /register and /login
    final String requestURI = request.getRequestURI();
    if (requestURI.equals("/api/auth/register") || requestURI.equals("/api/auth/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    //GET Jwt token
    final String authHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
    }

    if (jwt == null) {
      return;
    }

    //CHECK Email in database
    String jwtEmail = JwtUtil.extractEmail(jwt);
    if (userService.getByEmail(jwtEmail) == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (!JwtUtil.validateToken(jwt)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
