package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.UserService;
import com.app.zware.Util.PasswordUtil;
import com.app.zware.Validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
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

    // response
    CustomResponse customResponse = new CustomResponse();
    customResponse.setAll(true, "Get data of all user success", userService.getAllUsers());

    return new ResponseEntity<>(customResponse, HttpStatus.OK);


  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> show(@PathVariable("userId") Integer userId) {

    //Response
    CustomResponse customResponse = new CustomResponse();

    //Validation: Any Authenticated user
    //Validate
    String checkMessage = userValidator.checkGet(userId);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false, checkMessage, null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    customResponse.setAll(true, "get data of user with id " + userId + " success",
        userService.getById(userId));

    return new ResponseEntity<>(customResponse, HttpStatus.OK);

    //GET User
    //return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

  }

  @PostMapping("")
  public ResponseEntity<?> register(
      @RequestBody User user,
      HttpServletRequest request
  ) {
    //response
    CustomResponse customResponse = new CustomResponse();

    //Authorization: ADMIN ONLY
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin")) {
      customResponse.setAll(false, "You are not allowed", null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //Validation
    String checkMessage = userValidator.checkPost(user);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false, checkMessage, null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    //SAVE
    String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
    user.setPassword(hashedPassword);
    //userService.save(user);
    customResponse.setAll(true, "User created", userService.save(user));
    return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
  }

  //ko xoa vat li
  @DeleteMapping("/{userId}")
  public ResponseEntity<?> destroy(
      @PathVariable("userId") Integer userId,
      HttpServletRequest request
  ) {

    //Respose
    CustomResponse customResponse = new CustomResponse();

    //Validation: Only admin can delete
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin")) {
      customResponse.setAll(false, "You are not allowed", null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //Validate
    String checkMessage = userValidator.checkDelete(userId);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false, checkMessage, null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    //DELETE
    // Finally

    userService.deleteUserById(userId);
    customResponse.setAll(true, "User with id " + userId + " has been deleted", null);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<?> update(
      @PathVariable("userId") Integer userId,
      @RequestBody User newUser,
      HttpServletRequest request
  ) {

    //response
    CustomResponse customResponse = new CustomResponse();
    //Only admin or right user can edit
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin") && !requestMaker.getId().equals(userId)) {
      customResponse.setAll(false, "You are not allowed", null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //validate
    User mergedUser = userService.merge(userId, newUser);
    String checkMessage = userValidator.checkPut(userId, mergedUser);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false, checkMessage, null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    //finally
    //UPDATE
    User userUpdate = userService.update(userId, mergedUser);
    customResponse.setAll(true, "User update success", userUpdate);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);

  }

  // return me
  @GetMapping("/me")
  public ResponseEntity<?> me(HttpServletRequest request) {
    // response
    CustomResponse customResponse = new CustomResponse();

    User currentUser = userService.getRequestMaker(request);

    customResponse.setAll(true, "get current user data success", currentUser);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }

}
