package com.app.zware.Controllers;


import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Service.WarehouseItemsService;
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
@RequestMapping("/api/warehouseitems")
public class WarehouseItemsController {

  @Autowired
  WarehouseItemsService warehouseItemsService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<WarehouseItems> productList = warehouseItemsService.getAllWarehouseItems();
    if (productList.isEmpty()) {
      return new ResponseEntity<>("List WarehouseItems are empty", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(productList, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody WarehouseItems request) {
    return new ResponseEntity<>(warehouseItemsService.createWarehouseItems(request), HttpStatus.OK);
  }

  @GetMapping("/{warehouseitemid}")
  public ResponseEntity<?> show(@PathVariable("warehouseitemid") int warehouseitemId) {
    try {
      WarehouseItems warehouseItems = warehouseItemsService.getById(warehouseitemId);
      return new ResponseEntity<>(warehouseItems, HttpStatus.OK);

    } catch (RuntimeException e) {
      return new ResponseEntity<>("WarehouseItems not found", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{warehouseitemid}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseitemid") int warehouseitemId) {
    if (!warehouseItemsService.checkWarehouseItemsId(warehouseitemId)) {
      return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    } else {
      warehouseItemsService.deleteWarehouseItemsById(warehouseitemId);
      return new ResponseEntity<>("Product has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{warehouseitemid}")
  public ResponseEntity<?> update(@PathVariable int warehouseitemid,
      @RequestBody WarehouseItems request) {
    if (!warehouseItemsService.checkWarehouseItemsId(warehouseitemid)) {
      return new ResponseEntity<>("WarehouseItems not found", HttpStatus.NOT_FOUND);
    } else {
      warehouseItemsService.updateWarehouseItemsById(warehouseitemid, request);
      return new ResponseEntity<>("WarehouseItems has been updated successfully", HttpStatus.OK);
    }

  }
}
