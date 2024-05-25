package com.app.zware.Util;

import com.app.zware.Entities.Category;
import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategory(){
      return categoryRepository.findAll();
    }

    public Category createCategory(Category request){
        Category category = new Category();
        category.setName(request.getName());
       return categoryRepository.save(category);
    }

    public Category getCategoryById(int id){
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found Category"));
    }

    public void deleteCategoryById(int id){
        categoryRepository.deleteById(id);

    }
    public boolean checkIdExist(int id){
        return categoryRepository.existsById(id);
    }

    public Category updateCategoryById(int id,Category request){
        Category category = getCategoryById(id);
        if(request.getName()!=null){
            category.setName(request.getName());

        }
        return categoryRepository.save(category);
    }

}
