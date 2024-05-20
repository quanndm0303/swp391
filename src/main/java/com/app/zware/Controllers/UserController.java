package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Util.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> index(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> show(@PathVariable("userId") int userId)
    { return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);}

    @DeleteMapping("/{userId}")
    public String destroy(@PathVariable("userId") int userId) {
        userService.deleteUserById(userId);
        return "User has been deleted successfully";
    }

    @PutMapping("/{userId}")
    public String update(@PathVariable("userId") int userId, @RequestBody User userRequest){
        userService.updateUserById(userId, userRequest);
        return "User has been updated successfully";
    }
}
