package com.app.zware.Controllers;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Service.WarehouseZoneService;
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
@RequestMapping("/api/zones")
public class WarehouseZoneController {

  @Autowired
  WarehouseZoneService warehouseZoneService;

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
    return new ResponseEntity<>(warehouseZoneService.createWarehouseZone(warehouseZone),
        HttpStatus.OK);
  }

  @GetMapping("/{warehouseZoneId}")
  public ResponseEntity<?> show(@PathVariable("warehouseZoneId") int warehouseZoneId) {
    try {
      WarehouseZone warehouseZone = warehouseZoneService.getWarehouseZoneById(warehouseZoneId);
      return new ResponseEntity<>(warehouseZone, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>("Not found warehouseZone", HttpStatus.NOT_FOUND);
    }

  }

  @DeleteMapping("/{warehouseZoneId}")
  public ResponseEntity<?> destroy(@PathVariable("warehouseZoneId") int warehouseZoneId) {
    if (!warehouseZoneService.checkIdExist(warehouseZoneId)) {
      return new ResponseEntity<>("WarehouseZone not found", HttpStatus.NOT_FOUND);
    } else {
      warehouseZoneService.deleteWarehouseZoneById(warehouseZoneId);
      return new ResponseEntity<>("WarehouseZone has been deleted successfully", HttpStatus.OK);
    }
  }

  @PutMapping("/{warehouseZoneId}")
  public ResponseEntity<?> update(@PathVariable int warehouseZoneId,
      @RequestBody WarehouseZone request) {
    if (!warehouseZoneService.checkIdExist((warehouseZoneId))) {
      return new ResponseEntity<>("WarehouseZone not found", HttpStatus.NOT_FOUND);
    } else {
      warehouseZoneService.updateWarehouseZone(warehouseZoneId, request);
      return new ResponseEntity<>("Warehouse has been updated successfully", HttpStatus.OK);
    }
  }

}
