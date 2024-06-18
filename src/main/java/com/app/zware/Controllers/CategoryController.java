package com.app.zware.Controllers;

import com.app.zware.Entities.Category;
import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.CategoryService;
import java.util.List;

import com.app.zware.Service.UserService;
import com.app.zware.Validation.CategoryValidator;
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
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @Autowired
  CategoryValidator categoryValidator;

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Authorization: All
    List<Category> categoryList = categoryService.getCategory();
    //response
    CustomResponse customResponse = new CustomResponse();
    if (categoryList.isEmpty()) {
      //Not found
      customResponse.setAll(false, "Empty categoryList", null);
      return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
    } else {
      customResponse.setAll(true, "Get data of all Category success",categoryService.getCategory() );
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Category category, HttpServletRequest request) {
    //response
    CustomResponse customResponse = new CustomResponse();

    //Authorization: Only Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      customResponse.setAll(false, "You are not allowed", null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //validation
    String mess = categoryValidator.checkPost(category);
    if(!mess.isEmpty()){
      //error
      customResponse.setAll(false,mess,null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    } else {
      //approve
      customResponse.setAll(true,"Category has been created",categoryService.createCategory(category) );
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<?> show(@PathVariable("categoryId") Integer categoryId) {
    //Authorization: all

    //response
    CustomResponse customResponse = new CustomResponse();

    //validation
      String message = categoryValidator.checkGet(categoryId);
      if(!message.isEmpty()){
        //error
        customResponse.setAll(false,message,null);
        return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
      } else {
        //approve
        customResponse.setAll(true,"Get data of Category by id: " + categoryId +"success",categoryService.getCategoryById(categoryId));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
      }
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<?> destroy(@PathVariable("categoryId") Integer categoryId, HttpServletRequest request) {
    //response
    CustomResponse customResponse = new CustomResponse();

    //Authorization: Only Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }
    //validation
    String message = categoryValidator.checkDelete(categoryId);
    if (!message.isEmpty()) {
      //error
      customResponse.setAll(false,message,null);
      return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
    } else {
      //approve
      categoryService.deleteCategoryById(categoryId);
      customResponse.setAll(true,"Category with id: " + categoryId+"has been deleted",null);
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<?> update(@PathVariable Integer categoryId, @RequestBody Category request, HttpServletRequest userRequest) {
    //response
    CustomResponse customResponse = new CustomResponse();

    //Authorization: Only Admin
    User user = userService.getRequestMaker(userRequest);
    if(!user.getRole().equals("admin")){
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //merge infor
    Category updateCate = categoryService.merge(categoryId, request);

    //check validate
      String mess = categoryValidator.checkPut( categoryId,updateCate);
      if(!mess.isEmpty()){
        //error
        customResponse.setAll(false,mess,null);
        return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
      } else {
        //approve
        categoryService.update( updateCate);
        customResponse.setAll(true,"Category has been updated",updateCate);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
      }
    }
  }

