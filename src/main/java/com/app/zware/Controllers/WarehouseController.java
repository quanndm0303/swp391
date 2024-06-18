package com.app.zware.Controllers;


import com.app.zware.Entities.User;
import com.app.zware.Entities.Warehouse;
import com.app.zware.Service.UserService;
import com.app.zware.Service.WarehouseService;
import com.app.zware.Validation.WarehouseValidator;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Authorization : ALL

    return new ResponseEntity<>(warehouseService.getWarehouse(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Warehouse wareHouseRequest,
      HttpServletRequest request) {
    //Response
    HashMap<String, Object> response = new HashMap<>();

    // Authorization : Admin
    User user = userService.getRequestMaker(request);
    if (!user.getRole().equals("admin")) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //Validation
    String message = warehouseValidator.checkPost(wareHouseRequest);
    if (!message.isEmpty()) {
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    warehouseService.createWareHouse(wareHouseRequest);
    return new ResponseEntity<>("Warehouse has been created successfully", HttpStatus.OK);
  }

  @GetMapping("/{warehouseId}")
  public ResponseEntity<?> show(@PathVariable("warehouseId") Integer warehouseId) {
    //Authorization : ALL

    //Response
    HashMap<String, Object> response = new HashMap<>();

    // Validation
    String checkMessage = warehouseValidator.checkGet(warehouseId);
    if (!checkMessage.isEmpty()) {
      response.put("status", "fail");
      response.put("message", checkMessage);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    response.put("status", "success");
    response.put("message", "get data of warehouse with id " + warehouseId + " success");
    response.put("data", warehouseService.getWareHouseById(warehouseId));

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @GetMapping("/{warehouseId}/zones")

  @DeleteMapping("/{warehouseId}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseId") Integer warehouseId,
      HttpServletRequest request) {
    //Authorization : Admin

    User user = userService.getRequestMaker(request);
    if (!user.getRole().equals("admin")) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    String checkMessage = warehouseValidator.checkDetete(warehouseId);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    } else {
      warehouseService.deleteWareHouseById(warehouseId);
      return new ResponseEntity<>("Warehouse has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{warehouseId}")
  public ResponseEntity<?> update(@PathVariable int warehouseId,
      @RequestBody Warehouse requestWarehouse, HttpServletRequest request) {
    //Authorization : Admin
    User user = userService.getRequestMaker(request);
    if (!user.getRole().equals("admin")) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    Warehouse mergedWarehouse = warehouseService.merge(warehouseId, requestWarehouse);

    String checkMessage = warehouseValidator.checkPut(warehouseId, mergedWarehouse);

    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    Warehouse updateWarehouse = warehouseService.updateWarehouse(mergedWarehouse);
    return new ResponseEntity<>(updateWarehouse, HttpStatus.OK);

  }
}


