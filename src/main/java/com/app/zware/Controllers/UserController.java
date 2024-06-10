package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Service.UserService;
import com.app.zware.Util.PasswordUtil;
import com.app.zware.Validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserValidator userValidator;

  @GetMapping("")
  public ResponseEntity<?> index() {

    //Validation: Any Authenticated user
    //GET User
    List<User> listAllUser = userService.getAllUsers();
    if (!listAllUser.isEmpty()) {
      return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("List Users are empty", HttpStatus.OK);
    }

  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> show(@PathVariable("userId") Integer userId) {

    //Validation: Any Authenticated user
    //Validate
    String checkMessage = userValidator.checkGet(userId);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //GET User
    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

  }

  @PostMapping("")
  public ResponseEntity<?> register(
      @RequestBody User user,
      HttpServletRequest request
  ) {

    //Authorization: ADMIN ONLY
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin")){
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

  @DeleteMapping("/{userId}")
  public ResponseEntity<?> destroy(
      @PathVariable("userId") Integer userId,
      HttpServletRequest request
  ) {

    //Validation: Only admin can delete
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin")) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //Validate
    String checkMessage = userValidator.checkDelete(userId);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //DELETE
    userService.deleteUserById(userId);
    return new ResponseEntity<>("User has been deleted successfully", HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<?> update(
      @PathVariable("userId") Integer userId,
      @RequestBody User newUser,
      HttpServletRequest request
  ) {

    //Only admin or right user can edit
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin") && !requestMaker.getId().equals(userId)) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //validate
    User mergedUser = userService.merge(userId, newUser);
    String checkMessage = userValidator.checkPut(userId, mergedUser);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //UPDATE
      userService.update(userId, mergedUser);
      return new ResponseEntity<>("User has been Updated successfully", HttpStatus.OK);

  }

}
