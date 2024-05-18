package com.app.zware.Controllers;

import com.app.zware.Util.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }
}
