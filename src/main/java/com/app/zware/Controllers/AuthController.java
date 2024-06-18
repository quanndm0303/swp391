package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.UserService;
import com.app.zware.Util.JwtUtil;
import com.app.zware.Util.PasswordUtil;
import com.app.zware.Validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User request) {

    //Validation: anyone

    //Custom response
    CustomResponse customResponse = new CustomResponse();

    // request should contain only email and password
    if (!request.getEmail().matches("^(.+)@(\\S+)$") || request.getPassword().length() < 6) {
      customResponse.setAll(false, "Email or password is invalid", null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    //Login
    User user = userService.getByEmail(request.getEmail());
    if (user != null && PasswordUtil.checkPassword(request.getPassword(), user.getPassword())) {
      customResponse.setAll(true, "Login success", JwtUtil.generateToken(request.getEmail()));
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    customResponse.setAll(false, "Invalid credentials", null);
    return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);

  }

}
