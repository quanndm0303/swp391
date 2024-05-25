package com.app.zware.Service;


import com.app.zware.Entities.Product;
import com.app.zware.Repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getById(int id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not Found Product"));
  }

  public Product createProduct(Product request) {
    return productRepository.save(request);
  }

  public void deleteProductById(int id) {
    productRepository.deleteById(id);
  }

  public boolean checkIdProductExist(int id) {
    return productRepository.existsById(id);
  }

  public Product updateProductById(int id, Product productRequest) {
    Product product = getById(id);

    Optional.ofNullable(productRequest.getName()).ifPresent(product::setName);
    Optional.of(productRequest.getCategory_id()).ifPresent(product::setCategory_id);
    Optional.ofNullable(productRequest.getSupplier()).ifPresent(product::setSupplier);
    Optional.ofNullable(productRequest.getMeasure_unit()).ifPresent(product::setMeasure_unit);

    return productRepository.save(product);
  }
}
