package com.app.zware.Controllers;


import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Repositories.WarehouseItemsRepository;
import com.app.zware.Service.WarehouseItemsService;
import java.util.List;

import com.app.zware.Validation.WarehouseItemValidator;
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

  @Autowired
  WarehouseItemValidator warehouseItemValidator;

  @Autowired
  WarehouseItemsRepository warehouseItemsRepository;

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
    String checkMessage = warehouseItemValidator.checkPost(request);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }
    else {

      warehouseItemsService.createWarehouseItems(request);
      return new ResponseEntity<>("WarehouseItems has been created successfully",HttpStatus.OK);

    }

  }


  @GetMapping("/{warehouseitemid}")
  public ResponseEntity<?> show(@PathVariable("warehouseitemid") int warehouseitemId) {
    String checkMessage = warehouseItemValidator.checkGet(warehouseitemId);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.NOT_FOUND);
    }else {
      return new ResponseEntity<>(warehouseItemsService.getById(warehouseitemId),HttpStatus.OK);
    }
  }


  @DeleteMapping("/{warehouseitemid}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseitemid") int warehouseitemId) {
   String checkMessage = warehouseItemValidator.checkDelete(warehouseitemId);
   if(!checkMessage.isEmpty()){
     return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
   }
   else {
     warehouseItemsService.deleteWarehouseItemsById(warehouseitemId);
     return new ResponseEntity<>("WarehouseItem has been deleted successfully",HttpStatus.OK);
   }
  }

  @PutMapping("/{warehouseitemid}")
  public ResponseEntity<?> update(@PathVariable int warehouseitemid,
      @RequestBody WarehouseItems request) {
   WarehouseItems mergedWarehouseItem = warehouseItemsService.merger(warehouseitemid,request);

   //Validation
    String checkMessage = warehouseItemValidator.checkPut(warehouseitemid,mergedWarehouseItem);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }else {
      WarehouseItems updated = warehouseItemsService.update(mergedWarehouseItem);
      return new ResponseEntity<>(updated,HttpStatus.OK);
    }





  }

}
