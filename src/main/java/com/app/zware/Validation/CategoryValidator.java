package com.app.zware.Validation;

import com.app.zware.Entities.Category;
import com.app.zware.Entities.Product;
import com.app.zware.Repositories.CategoryRepository;
import com.app.zware.Repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ProductRepository productRepository;

  public String checkPost(Category category) {
    if (category.getName() == null || category.getName().isBlank()) {
      return "Category name is invalid";
    }
    Optional<Category> checkNameExist = categoryRepository.findByName(category.getName());
    if (checkNameExist.isPresent()) {
      return "Category name is exist";
    }
    return "";
  }

  public String checkPut(Integer id, Category categoryUpdated) {
    if (id == null || !checkCategoryId(id)) {
      return "Not found Category ID";
    }
    Category existCategory = categoryRepository.findByName(categoryUpdated.getName()).orElse(null);
    if (existCategory == null) {
      return "";
    } else {
      return existCategory.getId().equals(id) ? "" : "Category name is exist";
    }
  }

  public boolean checkCategoryId(Integer id) {
    return categoryRepository.existsByIdAndIsDeletedFalse(id);
  }

  public String checkGet(Integer categoryId) {
    if (!checkCategoryId(categoryId)) {
      return "Not Found Category ID";
    }
    return "";
  }

  public String checkDelete(Integer categoryId) {
    if (!checkCategoryId(categoryId)) {
      return "Not Found Category ID";
    }
    //find product by categoryId
    List<Product> listProduct = productRepository.findByCategoryId(categoryId);
    if (!listProduct.isEmpty()) {
      return "Cannot delete this category, there are some products in this category";
    }
    return "";
  }

}

