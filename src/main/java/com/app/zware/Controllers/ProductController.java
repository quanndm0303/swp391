package com.app.zware.Controllers;

import com.app.zware.Entities.Product;
import com.app.zware.Service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<Product> productList = productService.getAllProducts();
    if (productList.isEmpty()) {
      return new ResponseEntity<>("List Products are empty", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(productList, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Product product) {
    return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<?> show(@PathVariable("productId") int productId) {
    try {
      Product product = productService.getById(productId);
      return new ResponseEntity<>(product, HttpStatus.OK);

    } catch (RuntimeException e) {
      return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<?> destroy(@PathVariable("productId") int productId) {
    if (!productService.checkIdProductExist(productId)) {
      return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    } else {
      productService.deleteProductById(productId);
      return new ResponseEntity<>("Product has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{productId}")
  public ResponseEntity<?> update(@PathVariable int productId, @RequestBody Product request) {
    if (!productService.checkIdProductExist(productId)) {
      return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    } else {
      productService.updateProductById(productId, request);
      return new ResponseEntity<>("Product has been updated successfully", HttpStatus.OK);
    }

  }
}
