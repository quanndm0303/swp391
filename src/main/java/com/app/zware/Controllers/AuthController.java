package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import com.app.zware.RequestEntities.LoginRequest;
import com.app.zware.Util.JwtUtil;
import com.app.zware.Util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  UserRepository userRepository;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    if (userRepository.findByEmail(user.getEmail()) != null) {
      return new ResponseEntity<>("Email has been used", HttpStatus.FAILED_DEPENDENCY);
    }

    String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
    user.setPassword(hashedPassword);
    userRepository.save(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail());
    if (user != null && PasswordUtil.checkPassword(request.getPassword(), user.getPassword())) {
      return JwtUtil.generateToken(request.getEmail());
    } else {
      throw new RuntimeException("Invalid credentials");
    }

  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello from auth/hello";
  }
}
