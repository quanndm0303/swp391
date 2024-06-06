package com.app.zware.Service;

import com.app.zware.Entities.Category;
import com.app.zware.Repositories.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public List<Category> getCategory() {
    return categoryRepository.findAll();
  }

  public Category createCategory(Category request) {
    Category category = new Category();
    category.setName(request.getName());
    return categoryRepository.save(category);
  }

  public Category getCategoryById(int id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not Found Category"));
  }

  public void deleteCategoryById(int id) {
    categoryRepository.deleteById(id);

  }


  public Category updateCategoryById(int id, Category request) {
    Category category = getCategoryById(id);
    if (request.getName() != null) {
      category.setName(request.getName());

    }
    return categoryRepository.save(category);
  }

}
