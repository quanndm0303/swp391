package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Util.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getUserById(@PathVariable("userid") int userId)
    { return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);}

    @DeleteMapping("/{userid}")
    public void deleteUserById(@PathVariable("userid") int userId) {
        userService.deleteUserById(userId);
    }
}
