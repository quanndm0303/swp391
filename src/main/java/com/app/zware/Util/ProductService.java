package com.app.zware.Util;

import com.app.zware.Entities.Product;
import com.app.zware.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(int id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found Product"));
    }

    public Product createProduct(Product request){
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setSupplier(request.getSupplier());
        product.setMeasureUnit(request.getMeasureUnit());
        return productRepository.save(product);
    }

    public void deleteUserById(int id){
        productRepository.deleteById(id);
    }

    public boolean checkIdUserExist(int id){
        return productRepository.existsById(id);
    }

    public Product updateProductById(int id, Product productRequest){
        Product product = getProductById(id);
        if(productRequest.getName()!= null){
            product.setName(productRequest.getName());
        }
        if(productRequest.getSupplier() != null){
            product.setSupplier(productRequest.getSupplier());
        }
        if(productRequest.getMeasureUnit() != null){
            product.setMeasureUnit(productRequest.getMeasureUnit());
        }
        if(productRequest.getCategory() != null){
            product.setCategory(productRequest.getCategory());
        }
        return productRepository.save(product);
    }
}
