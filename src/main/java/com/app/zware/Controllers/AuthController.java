package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Service.UserService;
import com.app.zware.Util.JwtUtil;
import com.app.zware.Util.PasswordUtil;
import com.app.zware.Validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User request) {
    
    //Validation: anyone

    //Response
    HashMap<String, Object> response = new HashMap<>();

    // request should contain only email and password
    if (!request.getEmail().matches("^(.+)@(\\S+)$") || request.getPassword().length() < 6) {
      return new ResponseEntity<>("email or password is invalid", HttpStatus.BAD_REQUEST);
    }

    //Login
    User user = userService.getByEmail(request.getEmail());
    if (user != null && PasswordUtil.checkPassword(request.getPassword(), user.getPassword())) {
//      return new ResponseEntity<>(new Map)
      response.put("status","success");
      response.put("message","login success");
      response.put("token",JwtUtil.generateToken(request.getEmail()));
      return new ResponseEntity<>(response, HttpStatus.OK);
//      return JwtUtil.generateToken(request.getEmail());
    } else {
      response.put("status","fail");
      response.put("message","login fail");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

}
