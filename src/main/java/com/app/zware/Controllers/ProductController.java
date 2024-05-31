package com.app.zware.Controllers;

import com.app.zware.Entities.Product;
import com.app.zware.Service.ProductService;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

  @PostMapping("/{productid}/image")
  public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("productid") Integer productid) throws IOException {
    if(file.isEmpty()){
      return new ResponseEntity<>("Not found file!", HttpStatus.NOT_FOUND);
    } else if(!productService.checkIdProductExist(productid)) {
      return new ResponseEntity<>("Not found Id",HttpStatus.OK);
    } else {
      String uploadImage = productService.uploadImage(file, productid);
      return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

  }

  @GetMapping("/{productid}/image")
  public ResponseEntity<?> downloadImage(@PathVariable Integer productid) throws IOException {
    boolean productId = productService.checkIdProductExist(productid);
    if (!productId) {
      return new ResponseEntity<>("Not found Product", HttpStatus.NOT_FOUND);
    } else {
      byte[] imageData = productService.downloadImage(productid);
      if (imageData != null) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
      }
    }
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    // Tăng giới hạn kích thước tệp lên 50MB
    factory.setMaxFileSize(DataSize.parse("50MB"));
    // Tăng giới hạn kích thước tổng cả các tệp lên 100MB
    factory.setMaxRequestSize(DataSize.parse("100MB"));
    return factory.createMultipartConfig();
  }
}
