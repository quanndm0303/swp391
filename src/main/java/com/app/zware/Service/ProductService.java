package com.app.zware.Service;


import com.app.zware.Entities.Product;
import com.app.zware.Repositories.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


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

  public void deleteProductById(Integer id) {
    productRepository.deleteById(id);
  }

//  public boolean checkIdProductExist(int id) {
//    return productRepository.existsById(id);
//  }

  public Product updateProductById(int id, Product productRequest) {
    Product product = getById(id);

    Optional.ofNullable(productRequest.getName()).ifPresent(product::setName);
    Optional.of(productRequest.getCategory_id()).ifPresent(product::setCategory_id);
    Optional.ofNullable(productRequest.getSupplier()).ifPresent(product::setSupplier);
    Optional.ofNullable(productRequest.getMeasure_unit()).ifPresent(product::setMeasure_unit);

    return productRepository.save(product);
  }

  @Value("${image.storage.directory}")
  private String storageDirectory;

  public String uploadImage(MultipartFile file, Integer productid) throws IOException {
    // Tạo thư mục nếu chưa tồn tại
    File directory = new File(storageDirectory);
    if (!directory.exists()) {
      directory.mkdirs();
    }

    // Lưu tệp ảnh vào thư mục với tên chứa thời gian
    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    String fileExtension = getFileExtension(file.getOriginalFilename());
    String fileName = timestamp + fileExtension;
    Path filePath = Paths.get(storageDirectory, fileName);
    Files.write(filePath, file.getBytes());

    // Lưu thông tin ảnh vào cơ sở dữ liệu
    Product imageData = getById(productid);
    imageData.setImage(fileName);
    productRepository.save(imageData);

    return "File uploaded successfully: " + fileName;
  }


  public byte[] downloadImage(Integer productid) throws IOException {
    Product product = getById(productid);
    if(product.getImage() != null) {
      Path filePath = Paths.get(storageDirectory, product.getImage());
      return Files.readAllBytes(filePath);
    }
//    Path filePath = Paths.get(storageDirectory, product.getImage());
//
//    if (Files.exists(filePath)) {
//      return Files.readAllBytes(filePath);
//    }
     return null;
    }

  public String deleteImage(Integer productid) throws IOException {
    Product product = getById(productid);
    if (product.getImage() != null) {
      Path filePath = Paths.get(storageDirectory, product.getImage());
      Files.deleteIfExists(filePath);
      product.setImage(null);
      productRepository.save(product);
      return "Image deleted successfully";
    }
    return "Image not found";
  }

  private String getFileExtension(String fileName) {
    if (fileName == null) {
      return "";
    }
    int dotIndex = fileName.lastIndexOf('.');
    return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
  }

}
