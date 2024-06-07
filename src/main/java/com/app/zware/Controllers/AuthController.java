package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Service.UserService;
import com.app.zware.Util.JwtUtil;
import com.app.zware.Util.PasswordUtil;
import com.app.zware.Validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
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
  UserService userService;

  @Autowired
  UserValidator userValidator;

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody User user,
      HttpServletRequest request
  ) {

    //Authorization: ADMIN ONLY
    User requestMake = userService.getRequestMaker(request);
    if (!user.getRole().equals("admin")){
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    //Validation
    String checkMessage = userValidator.checkPost(user);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //SAVE
    String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
    user.setPassword(hashedPassword);
    userService.save(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public String login(@RequestBody User request) {
    
    //Validation: anyone

    // request should contain only email and password
    if (!request.getEmail().matches("^(.+)@(\\S+)$") || request.getPassword().length() < 6) {
      return "Email or Password is not valid";
    }

    //Login
    User user = userService.getByEmail(request.getEmail());
    if (user != null && PasswordUtil.checkPassword(request.getPassword(), user.getPassword())) {
      return JwtUtil.generateToken(request.getEmail());
    } else {
      return "Invalid credentials";
    }
  }

}
