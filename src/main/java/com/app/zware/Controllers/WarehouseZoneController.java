package com.app.zware.Controllers;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Service.WarehouseZoneService;
import java.util.List;

import com.app.zware.Validation.WarehouseZoneValidator;
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

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<WarehouseZone> warehouseZones = warehouseZoneService.getAll();

    if (warehouseZones.isEmpty()) {
      return new ResponseEntity<>("Empty WarehouseZones", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(warehouseZoneService.getAll(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody WarehouseZone warehouseZone) {
    String validator = warehouseZoneValidator.checkPost(warehouseZone);
    if(!validator.equals("Validation successful")){
      return new ResponseEntity<>(validator,HttpStatus.BAD_REQUEST);
    }else {
      warehouseZoneService.createWarehouseZone(warehouseZone);
      return new ResponseEntity<>("WarehouseZone has been created successful",HttpStatus.OK);
    }
  }

  @GetMapping("/{warehouseZoneId}")
  public ResponseEntity<?> show(@PathVariable("warehouseZoneId") int warehouseZoneId) {
    String validator = warehouseZoneValidator.checkGet(warehouseZoneId);
    if(!validator.isEmpty()){
      return new ResponseEntity<>(validator,HttpStatus.BAD_REQUEST);
    }else {
      return new ResponseEntity<>(warehouseZoneService.getWarehouseZoneById(warehouseZoneId),HttpStatus.OK);
    }

  }

  @DeleteMapping("/{warehouseZoneId}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseZoneId") int warehouseZoneId) {
    String validator = warehouseZoneValidator.checkDelete(warehouseZoneId);
    if(!validator.isEmpty()){
      return new ResponseEntity<>(validator,HttpStatus.BAD_REQUEST);
    }else {
      warehouseZoneService.deleteWarehouseZoneById(warehouseZoneId);
      return new ResponseEntity<>("WarehouseZone has been deleted successfully",HttpStatus.OK);
    }

  }

  @PutMapping("/{warehouseZoneId}")
  public ResponseEntity<?> update(@PathVariable int warehouseZoneId,
      @RequestBody WarehouseZone request) {
    String validatorGet = warehouseZoneValidator.checkGet(warehouseZoneId);
    String validatorPut = warehouseZoneValidator.checkPut(request);
    if(!validatorGet.isEmpty()){
      return new ResponseEntity<>(validatorGet,HttpStatus.BAD_REQUEST);
    } else if (!validatorPut.equals("Validation successful")) {
      return new ResponseEntity<>(validatorPut,HttpStatus.BAD_REQUEST);
    }else {
      warehouseZoneService.updateWarehouseZone(warehouseZoneId,request);
      return new ResponseEntity<>("WarehouseZone has been updated successfully",HttpStatus.OK);
    }
  }

}
