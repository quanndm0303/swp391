package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Util.UserService;
import java.util.HashMap;
import java.util.List;
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
        List<User> listAllUser = userService.getAllUsers();
        if(!listAllUser.isEmpty()) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("List Users are empty", HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> show(@PathVariable("userId") int userId){
        if(userService.checkIdUserExist(userId)) {
            return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> destroy(@PathVariable("userId") int userId) {
        if(userService.checkIdUserExist(userId)) {
            userService.deleteUserById(userId);
            return new ResponseEntity<>("User has been deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable("userId") int userId, @RequestBody User userRequest){
        if(userService.checkIdUserExist(userId)) {
            userService.update(userId, userRequest);
            return new ResponseEntity<>("User has been Updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
