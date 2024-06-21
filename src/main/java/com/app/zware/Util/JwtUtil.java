package com.app.zware.Util;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {

  private static final String SECRET_KEY = "your_secret_key";
  private static final String HMAC_ALGO = "HmacSHA256";

  public static String generateToken(String email) {
    long currentTimeMillis = System.currentTimeMillis();
    long expirationTimeMillis = currentTimeMillis + 1000 * 60 * 60; // 1 hours validity

    String header = Base64.getUrlEncoder()
        .encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
    String payload = Base64.getUrlEncoder().encodeToString(
        ("{\"sub\":\"" + email + "\",\"exp\":" + expirationTimeMillis + "}").getBytes());
    String signature = sign(header + "." + payload);

    return header + "." + payload + "." + signature;
  }

  private static String sign(String data) {
    try {
      Mac hmacSha256 = Mac.getInstance(HMAC_ALGO);
      SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
          HMAC_ALGO);
      hmacSha256.init(secretKey);
      byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().encodeToString(hash);
    } catch (Exception e) {
      throw new RuntimeException("Error while signing the token", e);
    }
  }

  public static String extractEmail(String token) {
    String[] parts = token.split("\\.");
    if (parts.length < 3) {
      throw new IllegalArgumentException("Invalid token");
    }
    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
    return payload.substring(payload.indexOf("\"sub\":\"") + 7, payload.indexOf("\",\"exp\""));
  }

  public static boolean isTokenExpired(String token) {
    String[] parts = token.split("\\.");
    if (parts.length < 3) {
      throw new IllegalArgumentException("Invalid token");
    }
    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
    long expiration = Long.parseLong(
        payload.substring(payload.indexOf("\"exp\":") + 6, payload.length() - 1));
    return expiration < System.currentTimeMillis();
  }

  public static boolean validateToken(String token) {

    //CHECK expired
    if (isTokenExpired(token)) {
      return false;
    }

    //CHECK valid email
    //CHECKED in JwtFilter

    //CHECK signature
    String[] parts = token.split("\\.");
    if (parts.length < 3) {
      throw new IllegalArgumentException("Invalid token");
    }
    String headerAndPayload = parts[0] + "." + parts[1];

    String signature = parts[2];
    String expectedSignature = sign(headerAndPayload);
    return signature.equals(expectedSignature);
  }

  //some Methods with HttpServletRequest

  public static String getJwtToken(HttpServletRequest request) {
    //GET Jwt token
    final String authHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
    }
    return jwt;
  }
}

