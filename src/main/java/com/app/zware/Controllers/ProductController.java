package com.app.zware.Controllers;

import com.app.zware.Entities.Product;
import com.app.zware.Entities.User;
import com.app.zware.Service.ProductService;

import java.io.IOException;
import java.util.List;

import com.app.zware.Service.UserService;
import com.app.zware.Validation.ProductValidator;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletRequest;
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

  @Autowired
  ProductValidator productValidator;

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Authorization: All
    List<Product> productList = productService.getAllProducts();
    if (productList.isEmpty()) {
      return new ResponseEntity<>("List Products are empty", HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(productList, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Product product, HttpServletRequest request) {
    //Authorization: Only Admin can create products
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //validation
    String messages = productValidator.checkPost(product);
    product.setImage(null);
    if(messages.isEmpty()) {
      //error
      return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
    } else {
      //approve
      return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{productId}")
  public ResponseEntity<?> show(@PathVariable("productId") Integer productId) {

    //Authorization: All
      String messages = productValidator.checkGet(productId);
      if(messages.isEmpty()){
        Product product = productService.getById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
      }

  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<?> destroy(@PathVariable("productId") Integer productId, HttpServletRequest request) {
    //Authorization : Only Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //validation
    String msg = productValidator.checkDelete(productId);
    if (!msg.isEmpty()) {
      //error
      return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    } else {
      //approve
      productService.deleteProductById(productId);
      return new ResponseEntity<>("Product has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{productId}")
  public ResponseEntity<?> update(@PathVariable Integer productId, @RequestBody Product request, HttpServletRequest userRequest) {
    //Authorization: Only Admin
    User user = userService.getRequestMaker(userRequest);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //set image of new update = null
    request.setImage(null);

    //merge infor
    Product updatedProduct = productService.merger(productId, request);

    //check validate
      String messages = productValidator.checkPut( productId,request);

      if(!messages.isEmpty()) {
        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
      } else {
        productService.update( updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
  }

  @PostMapping("/{productid}/image")
  public ResponseEntity<?> putImage(@RequestParam("image") MultipartFile file, @PathVariable("productid") Integer productid ,
                                    HttpServletRequest request) throws IOException {
    //Authorization: Only admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    //validation
    String msg = productValidator.checkGet(productid);
    if(file.isEmpty()){
      return new ResponseEntity<>("Not found file!", HttpStatus.BAD_REQUEST);
    } else if(!msg.isEmpty()) {
      return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
    } else {
      String uploadImage = productService.uploadImage(file, productid);
      return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }
  }

  @GetMapping("/{productid}/image")
  public ResponseEntity<?> showImage(@PathVariable Integer productid, HttpServletRequest request) throws IOException {
    //Authorization: Only admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //validation
    String msg = productValidator.checkGet(productid);
    if (!msg.isEmpty()) {
      return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    } else {
      byte[] imageData = productService.downloadImage(productid);
      if (imageData != null) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image not found");
      }
    }
  }

  @DeleteMapping("/{productid}/image")
  public ResponseEntity<?> destroyImage(@PathVariable Integer productid, HttpServletRequest request) throws IOException {
    //Authorization: Only admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    //validation
    String msg = productValidator.checkGet(productid);
    if (!msg.isEmpty()) {
      return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    } else {
      String result = productService.deleteImage(productid);
      if (result.equals("Image deleted successfully")) {
        return ResponseEntity.status(HttpStatus.OK).body(result);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
      }
    }
  }

//  @Bean
//  public MultipartConfigElement multipartConfigElement() {
//    MultipartConfigFactory factory = new MultipartConfigFactory();
//    // Tăng giới hạn kích thước tệp lên 50MB
//    factory.setMaxFileSize(DataSize.parse("50MB"));
//    // Tăng giới hạn kích thước tổng cả các tệp lên 100MB
//    factory.setMaxRequestSize(DataSize.parse("100MB"));
//    return factory.createMultipartConfig();
//  }
}
