package com.app.zware.Util;

import com.app.zware.Entities.Product;
import com.app.zware.Entities.User;
import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found WareHouse"));
    }

    public Product createProduct(Product request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setSupplier(request.getSupplier());
        product.setCategory_id(request.getCategory_id());
        product.setMeasure_unit(request.getMeasure_unit());
        return productRepository.save(product);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public boolean checkIdProductExist(int id) {
        return productRepository.existsById(id);
    }

    public Product updateProductById(int id, Product productRequest){
        Product product = getById(id);

        Optional.ofNullable(productRequest.getName()).ifPresent(product::setName);
        Optional.of(productRequest.getCategory_id()).ifPresent(product::setCategory_id);
        Optional.ofNullable(productRequest.getSupplier()).ifPresent(product::setSupplier);
        Optional.ofNullable(productRequest.getMeasure_unit()).ifPresent(product::setMeasure_unit);

        return productRepository.save(product);
    }
}
