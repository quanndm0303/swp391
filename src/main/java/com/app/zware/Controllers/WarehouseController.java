package com.app.zware.Controllers;


import com.app.zware.Entities.Warehouse;
import com.app.zware.Service.WarehouseService;
import com.app.zware.Validation.WarehouseValidator;
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
@RequestMapping("/api/warehouses")
public class WarehouseController {

  @Autowired
  WarehouseService warehouseService;

  @Autowired
  WarehouseValidator warehouseValidator;

  @GetMapping("")
  public ResponseEntity<?> index() {
    return new ResponseEntity<>(warehouseService.getWarehouse(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Warehouse wareHouseRequest) {
    String validation = warehouseValidator.checkPost(wareHouseRequest);
    if(!validation.equals("Validation successful")){
      return new ResponseEntity<>(validation,HttpStatus.BAD_REQUEST);
    }
    warehouseService.createWareHouse(wareHouseRequest);
    return new ResponseEntity<>("Warehouse has been created successfully", HttpStatus.OK);
  }

  @GetMapping("/{warehouseId}")
  public ResponseEntity<?> show(@PathVariable("warehouseId") Integer warehouseId) {
    String checkMessage = warehouseValidator.checkGet(warehouseId);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }else {
      return new ResponseEntity<>(warehouseService.getWareHouseById(warehouseId),HttpStatus.OK);
    }
  }

  @DeleteMapping("/{warehouseId}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseId") Integer warehouseId) {
    String checkMessage = warehouseValidator.checkDetete(warehouseId);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    } else {
      warehouseService.deleteWareHouseById(warehouseId);
      return new ResponseEntity<>("Warehouse has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{warehouseId}")
  public ResponseEntity<?> update(@PathVariable int warehouseId, @RequestBody Warehouse request) {
    String validationPut = warehouseValidator.checkPut(request);
    String validationGet = warehouseValidator.checkGet(warehouseId);
      if(!validationGet.isEmpty()){
        return new ResponseEntity<>(validationGet,HttpStatus.BAD_REQUEST);
      }
      if (!validationPut.equals("Validation successful")) {
         return new ResponseEntity<>(validationPut,HttpStatus.BAD_REQUEST);
    } else {
      warehouseService.updateWarehouse(warehouseId, request);
      return new ResponseEntity<>("Warehouse has been updated successfully", HttpStatus.OK);
    }

  }
}


