package com.app.zware.Controllers;

import com.app.zware.Entities.Category;
import com.app.zware.Service.CategoryService;
import java.util.List;

import com.app.zware.Validation.CategoryValidator;
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

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<Category> categoryList = categoryService.getCategory();
    if (categoryList.isEmpty()) {
      return new ResponseEntity<>("Empty categoryList", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(categoryService.getCategory(), HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Category category) {
    String mess = categoryValidator.checkPost(category);
    if(!mess.isEmpty()){
      return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
    }
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<?> show(@PathVariable("categoryId") Integer categoryId) {
      String message = categoryValidator.checkGet(categoryId);
      if(!message.isEmpty()){
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
      } else {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
      }
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<?> destroy(@PathVariable("categoryId") Integer categoryId) {
    String message = categoryValidator.checkDelete(categoryId);
    if (!message.isEmpty()) {
      return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    } else {
      categoryService.deleteCategoryById(categoryId);
      return new ResponseEntity<>("Category has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<?> update(@PathVariable Integer categoryId, @RequestBody Category request) {
    String message = categoryValidator.checkGet(categoryId);
    if (!message.isEmpty()) {
      return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    } else {
      String mess = categoryValidator.checkPut(request);
      if(!mess.isEmpty()){
        return new ResponseEntity<>(mess,HttpStatus.BAD_REQUEST);
      } else {
        categoryService.updateCategoryById(categoryId, request);
        return new ResponseEntity<>("Category has been updated successfully", HttpStatus.OK);
      }
    }
  }
}
