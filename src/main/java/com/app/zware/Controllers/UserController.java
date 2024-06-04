package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {

    //Any Authenticated user can view
    //GET User
    List<User> listAllUser = userService.getAllUsers();
    if (!listAllUser.isEmpty()) {
      return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("List Users are empty", HttpStatus.OK);
    }

  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> show(@PathVariable("userId") int userId) {

    //Any Authenticated user can view
    //GET User
    if (userService.checkIdUserExist(userId)) {
      return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<?> destroy(
      @PathVariable("userId") int userId,
      HttpServletRequest request
  ) {

    //Only admin can delete
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin") && !requestMaker.getId().equals(userId)) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //DELETE
    if (userService.checkIdUserExist(userId)) {
      userService.deleteUserById(userId);
      return new ResponseEntity<>("User has been deleted successfully", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

  }

  @PutMapping("/{userId}")
  public ResponseEntity<?> update(
      @PathVariable("userId") Integer userId,
      @RequestBody User requestBody,
      HttpServletRequest request
  ) {

    //Only admin or right user can edit
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin") && !requestMaker.getId().equals(userId)) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //UPDATE
    if (userService.checkIdUserExist(userId)) {
      userService.update(userId, requestBody);
      return new ResponseEntity<>("User has been Updated successfully\n", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

  }

}
