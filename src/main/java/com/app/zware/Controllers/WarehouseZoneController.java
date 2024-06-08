package com.app.zware.Controllers;

import com.app.zware.Entities.User;
import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Service.UserService;
import com.app.zware.Service.WarehouseZoneService;
import java.util.List;

import com.app.zware.Validation.WarehouseZoneValidator;
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
@RequestMapping("/api/zones")
public class WarehouseZoneController {

  @Autowired
  WarehouseZoneService warehouseZoneService;

  @Autowired
  WarehouseZoneValidator warehouseZoneValidator;

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Authorization : ALL

    List<WarehouseZone> warehouseZones = warehouseZoneService.getAll();

    if (warehouseZones.isEmpty()) {
      return new ResponseEntity<>("Empty WarehouseZones", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(warehouseZoneService.getAll(), HttpStatus.OK);
  }


  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody WarehouseZone warehouseZone, HttpServletRequest request) {
    //Authorization : Admin

    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }

    String checkMessage = warehouseZoneValidator.checkPost(warehouseZone);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }else {
      warehouseZoneService.createWarehouseZone(warehouseZone);
      return new ResponseEntity<>("WarehouseZone has been created successful",HttpStatus.OK);
    }
  }


  @GetMapping("/{warehouseZoneId}")
  public ResponseEntity<?> show(@PathVariable("warehouseZoneId") int warehouseZoneId) {
    //Authorization : ALL

    String checkMessage= warehouseZoneValidator.checkGet(warehouseZoneId);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }else {
      return new ResponseEntity<>(warehouseZoneService.getWarehouseZoneById(warehouseZoneId),HttpStatus.OK);
    }

  }



  @DeleteMapping("/{warehouseZoneId}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseZoneId") int warehouseZoneId,HttpServletRequest request) {
    //Authorization : Admin

    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }

    String checkMessage = warehouseZoneValidator.checkDelete(warehouseZoneId);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }else {
      warehouseZoneService.deleteWarehouseZoneById(warehouseZoneId);
      return new ResponseEntity<>("WarehouseZone has been deleted successfully",HttpStatus.OK);
    }


  }

  @PutMapping("/{warehouseZoneId}")
  public ResponseEntity<?> update(@PathVariable int warehouseZoneId,
      @RequestBody WarehouseZone requestWarehouseZone,HttpServletRequest request) {
    //Authorization : Admin

    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }
    

    WarehouseZone mergedWarehouseZone = warehouseZoneService.merger(warehouseZoneId,requestWarehouseZone);

    //Validate
    String checkMessage = warehouseZoneValidator.checkPut(warehouseZoneId,mergedWarehouseZone);
    if(!checkMessage.isEmpty()){
      return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
    }


    //Update
    WarehouseZone updated = warehouseZoneService.update(mergedWarehouseZone);
    return new ResponseEntity<>(updated,HttpStatus.OK);
  }

}
