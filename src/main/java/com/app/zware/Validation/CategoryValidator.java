package com.app.zware.Validation;

import com.app.zware.Entities.Category;
import com.app.zware.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryValidator {
    @Autowired
    CategoryRepository categoryRepository;

    public String checkPost(Category category) {
        if (category.getName() == null || category.getName().isBlank() ) {
            return "Category name is invalid";
        }
        Optional<Category> checkNameExist = categoryRepository.findByName(category.getName());
        if (checkNameExist.isPresent()) {
            return "Category name is exist";
        }
        return "";
    }

    public String checkPut(Integer id, Category category) {
        if( id == null || !checkCategoryId(id)){
            return "Not found Category ID";
        }
        return checkPost(category);
    }

    public boolean checkCategoryId(Integer id){
        return categoryRepository.existsById(id);
    }

    public String checkGet(Integer categoryId){
        if(!checkCategoryId(categoryId)){
            return "Not Found Category ID";
        }
        return "";
    }

    public String checkDelete(Integer categoryId){
        return checkGet(categoryId);
    }

}

