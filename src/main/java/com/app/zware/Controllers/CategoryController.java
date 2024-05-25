package com.app.zware.Controllers;

import com.app.zware.Entities.Category;
import com.app.zware.Entities.Warehouse;
import com.app.zware.Util.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> index() {
        List<Category> categoryList = categoryService.getCategory();
        if(categoryList.isEmpty()){
            return new ResponseEntity<>("Empty categoryList", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(categoryService.getCategory(),HttpStatus.OK);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody Category category){
            return new ResponseEntity<>(categoryService.createCategory(category),HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> show (@PathVariable("categoryId") int categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(category, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> destroy (@PathVariable("categoryId") int categoryId){
        if(!categoryService.checkIdExist(categoryId)){
            return new ResponseEntity<>("Not Found Category",HttpStatus.NOT_FOUND);
        }else {
            categoryService.deleteCategoryById(categoryId);
            return new ResponseEntity<>("Category has been deleted successfully", HttpStatus.OK);
        }
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> update (@PathVariable int categoryId, @RequestBody Category request){
      if(!categoryService.checkIdExist(categoryId)){
          return new ResponseEntity<>("Not Found Category",HttpStatus.NOT_FOUND);
      }else {
          categoryService.updateCategoryById(categoryId,request);
          return new ResponseEntity<>("Category has been updated successfully",HttpStatus.OK);
      }
    }
}
