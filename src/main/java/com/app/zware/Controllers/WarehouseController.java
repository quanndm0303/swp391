package com.app.zware.Controllers;


import com.app.zware.Entities.Warehouse;
import com.app.zware.Service.WarehouseService;
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

  @GetMapping("")
  public ResponseEntity<?> index() {
    return new ResponseEntity<>(warehouseService.getWarehouse(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Warehouse wareHouseRequest) {
    return new ResponseEntity<>(warehouseService.createWareHouse(wareHouseRequest), HttpStatus.OK);
  }

  @GetMapping("/{warehouseId}")
  public ResponseEntity<?> show(@PathVariable("warehouseId") int warehouseId) {
    try {
      Warehouse warehouse = warehouseService.getWareHouseById(warehouseId);
      return new ResponseEntity<>(warehouse, HttpStatus.OK);

    } catch (RuntimeException e) {
      return new ResponseEntity<>("Warehouse not found", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{warehouseId}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseId") int warehouseId) {
    if (!warehouseService.checkIdExist(warehouseId)) {
      return new ResponseEntity<>("Warehouse not found", HttpStatus.NOT_FOUND);
    } else {
      warehouseService.deleteWareHouseById(warehouseId);
      return new ResponseEntity<>("Warehouse has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{warehouseId}")
  public ResponseEntity<?> update(@PathVariable int warehouseId, @RequestBody Warehouse request) {
    if (!warehouseService.checkIdExist(warehouseId)) {
      return new ResponseEntity<>("Warehouse not found", HttpStatus.NOT_FOUND);
    } else {
      warehouseService.updateWarehouse(warehouseId, request);
      return new ResponseEntity<>("Warehouse has been updated successfully", HttpStatus.OK);
    }

  }
}


