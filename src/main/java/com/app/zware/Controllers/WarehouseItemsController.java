package com.app.zware.Controllers;


import com.app.zware.Entities.User;
import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseZoneRespository;
import com.app.zware.Service.UserService;
import com.app.zware.Service.WarehouseItemsService;
import com.app.zware.Service.WarehouseZoneService;
import com.app.zware.Validation.WarehouseItemValidator;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
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
  WarehouseZoneRespository warehouseZoneRespository;

  @Autowired
  UserService userService;

  @Autowired
  WarehouseZoneService warehouseZoneService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Authorization : ALL

    List<WarehouseItems> productList = warehouseItemsService.getAllWarehouseItems();
    if (productList.isEmpty()) {
      return new ResponseEntity<>("List WarehouseItems are empty", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(productList, HttpStatus.OK);
    }
  }


  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody WarehouseItems requestWarehouseItem,HttpServletRequest request) {
    //Authorization : Admin and manager

    User user = userService.getRequestMaker(request);

    WarehouseZone warehouseZone = warehouseZoneService.getWarehouseZoneById(requestWarehouseItem.getZone_id());

    if (!user.getRole().equals("admin")&&!user.getWarehouse_id().equals(warehouseZone.getWarehouse_id())){
      return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }

    String checkMessage = warehouseItemValidator.checkPost(requestWarehouseItem);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    } else {

      warehouseItemsService.createWarehouseItems(requestWarehouseItem);
      return new ResponseEntity<>("WarehouseItems has been created successfully", HttpStatus.OK);

    }

  }


  @GetMapping("/{warehouseitemid}")
  public ResponseEntity<?> show(@PathVariable("warehouseitemid") int warehouseitemId) {
    //Authorization : ALL

    String checkMessage = warehouseItemValidator.checkGet(warehouseitemId);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(warehouseItemsService.getById(warehouseitemId), HttpStatus.OK);
    }
  }


  @DeleteMapping("/{warehouseitemid}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseitemid") int warehouseitemId, HttpServletRequest request) {
    // Authorization : Admin and manager

    User user = userService.getRequestMaker(request);
    WarehouseItems warehouseItems = warehouseItemsService.getById(warehouseitemId);
    if (warehouseItems == null) {
      return new ResponseEntity<>("WarehouseItem not found", HttpStatus.NOT_FOUND);
    }
    WarehouseZone warehouseZone = warehouseZoneService.getWarehouseZoneById(warehouseItems.getZone_id());

   //Authorization
    if(!user.getRole().equals("admin")&&(!user.getWarehouse_id().equals(warehouseZone.getWarehouse_id()))){
      return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }


    String checkMessage = warehouseItemValidator.checkDelete(warehouseitemId);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    } else {
      warehouseItemsService.deleteWarehouseItemsById(warehouseitemId);
      return new ResponseEntity<>("WarehouseItem has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{warehouseitemid}")
  public ResponseEntity<?> update(@PathVariable int warehouseitemid,
      @RequestBody WarehouseItems requestWarehouseItem,HttpServletRequest request) {
    //Authorization
    User user = userService.getRequestMaker(request);

    WarehouseItems warehouseItems = warehouseItemsService.getById(warehouseitemid);
    if(warehouseItems==null){
      return new ResponseEntity<>("WarehouseItem not found",HttpStatus.NOT_FOUND);
    }
    WarehouseZone warehouseZone = warehouseZoneService.getWarehouseZoneById(warehouseItems.getZone_id());
    if(!user.getRole().equals("admin")&&!user.getWarehouse_id().equals(warehouseZone.getWarehouse_id())){
      return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }


    WarehouseItems mergedWarehouseItem = warehouseItemsService.merge(warehouseitemid, requestWarehouseItem);

    //Validation
    String checkMessage = warehouseItemValidator.checkPut(warehouseitemid, mergedWarehouseItem);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    } else {
      WarehouseItems updated = warehouseItemsService.update(mergedWarehouseItem);
      return new ResponseEntity<>(updated, HttpStatus.OK);
    }


  }

}
