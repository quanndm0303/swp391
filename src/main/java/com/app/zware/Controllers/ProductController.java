package com.app.zware.Controllers;

import com.app.zware.Entities.Product;
import com.app.zware.Util.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> index(){
        List<Product> productList = productService.getAllProduct();
        if(!productList.isEmpty()){
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("List Products are empty", HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody Product productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> show(@PathVariable("productId") int productId){
        if(productService.checkIdUserExist(productId)) {
            return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> destroy(@PathVariable("productId") int productId) {
        if(productService.checkIdUserExist(productId)) {
            productService.deleteUserById(productId);
            return new ResponseEntity<>("Product has been deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> update(@PathVariable("productId") int productId, @RequestBody Product userRequest){
        if(productService.checkIdUserExist(productId)) {
            productService.updateProductById(productId, userRequest);
            return new ResponseEntity<>("Product has been Updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}
