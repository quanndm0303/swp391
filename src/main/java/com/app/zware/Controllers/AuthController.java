package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import com.app.zware.RequestEntities.LoginRequest;
import com.app.zware.Util.JwtUtil;
import com.app.zware.Util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserRepository userRepository;

  @Autowired
  public AuthController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
    user.setPassword(hashedPassword);
    userRepository.save(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest request) {

      User user = userRepository.getByEmail(request.getEmail());
      if (user!=null && PasswordUtil.checkPassword(request.getPassword(), user.getPassword())){
        return JwtUtil.generateToken(request.getEmail());
      } else{
        throw new RuntimeException("Invalid credentials");
      }
      
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello from auth/hello";
  }
}
