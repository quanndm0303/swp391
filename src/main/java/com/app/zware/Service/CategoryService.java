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
//    Category category = new Category();
    request.setIsdeleted(false);
    return categoryRepository.save(request);


  }

  public Category getCategoryById(Integer id) {
    return categoryRepository.findById(id)
        .orElse(null);
  }

  public void deleteCategoryById(Integer id) {
    Category category = getCategoryById(id);
    category.setIsdeleted(true);
    categoryRepository.save(category);
//  categoryRepository.deleteById(id);
  }


  public Category merge(Integer id, Category request) {
    Category oldCategory = getCategoryById(id);
    if (oldCategory == null) {
      return null;
    }
    oldCategory.setIsdeleted(false);
    Optional.ofNullable(request.getName()).ifPresent(oldCategory::setName);

    return oldCategory;
  }

  public Category update(Category category) {

    return categoryRepository.save(category);
  }

}
