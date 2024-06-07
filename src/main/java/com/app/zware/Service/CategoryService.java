package com.app.zware.Service;

import com.app.zware.Entities.Category;
import com.app.zware.Repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;

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

  public Category getCategoryById(Integer id) {
    return categoryRepository.findById(id)
        .orElse(null);
  }

  public void deleteCategoryById(Integer id) {
    categoryRepository.deleteById(id);

  }


  public Category merge(Integer id, Category request) {
    Category oldCategory = getCategoryById(id);
    if (oldCategory==null) {
      return null;
    }

      Optional.ofNullable(request.getName()).ifPresent(oldCategory::setName);


    return oldCategory;
  }

  public Category update(Category category){
    return categoryRepository.save(category);
  }

}
