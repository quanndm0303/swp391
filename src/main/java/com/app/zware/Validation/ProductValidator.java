package com.app.zware.Validation;

import com.app.zware.Entities.Product;
import com.app.zware.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    @Autowired
    ProductRepository productRepository;

    public String checkPost(Product product) {
        if(product.getName() == null || product.getName().isEmpty()) {
            return "Name is not valid !";
        }
        if(product.getSupplier()== null || product.getSupplier().isEmpty()) {
            return "Supplier is not valid";
        }
        if(product.getMeasure_unit()==null || product.getMeasure_unit().isEmpty()) {
            return "Measure unit is not valid";
        }
        if(product.getCategory_id() == null|| !productRepository.existsByIdAndIsDeletedFalse(product.getCategory_id())) {
            return "Not found Category ID";
        }
        return "";
    }

    public String checkPut(Integer id, Product product){
        if(id == null || !checkProductId(id)){
            return "Not found product ID";
        }
//        if(product.getCategory_id() == null|| !productRepository.existsByIdAndIsDeletedFalse(product.getCategory_id())) {
//            return "Not found Category ID";
//        }
        return checkPost(product);
    }

    public String checkGet(Integer id){
        if(!checkProductId(id)){
            return "Not found ID product";
        }
        return "";
    }

    public String checkDelete(Integer id){
        return checkGet(id);
    }

    public boolean checkProductId(Integer id){
        return productRepository.existsByIdAndIsDeletedFalse(id);
    }



}
