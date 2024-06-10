package com.app.zware.Controllers;

import com.app.zware.Entities.Category;
import com.app.zware.Entities.User;
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
    if (categoryList.isEmpty()) {
      return new ResponseEntity<>("Empty categoryList", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(categoryService.getCategory(), HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Category category, HttpServletRequest request) {
    //Authorization: Only Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //validation
    String mess = categoryValidator.checkPost(category);
    if(!mess.isEmpty()){
      //error
      return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
    } else {
      //approve
      return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
    }
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<?> show(@PathVariable("categoryId") Integer categoryId) {
    //Authorization: all

    //validation
      String message = categoryValidator.checkGet(categoryId);
      if(!message.isEmpty()){
        //error
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
      } else {
        //approve
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
      }
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<?> destroy(@PathVariable("categoryId") Integer categoryId, HttpServletRequest request) {
    //Authorization: Only Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    //validation
    String message = categoryValidator.checkDelete(categoryId);
    if (!message.isEmpty()) {
      //error
      return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    } else {
      //approve
      categoryService.deleteCategoryById(categoryId);
      return new ResponseEntity<>("Category has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<?> update(@PathVariable Integer categoryId, @RequestBody Category request, HttpServletRequest userRequest) {
    //Authorization: Only Admin
    User user = userService.getRequestMaker(userRequest);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //merge infor
    Category updateCate = categoryService.merge(categoryId, request);

    //check validate
      String mess = categoryValidator.checkPut( categoryId,updateCate);
      if(!mess.isEmpty()){
        //error
        return new ResponseEntity<>(mess,HttpStatus.BAD_REQUEST);
      } else {
        //approve
        categoryService.update( updateCate);
        return new ResponseEntity<>(updateCate, HttpStatus.OK);
      }
    }
  }

