package com.app.zware.Controllers;


import com.app.zware.Entities.Warehouse;
import com.app.zware.Util.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @GetMapping("")
    public ResponseEntity<?> index(){
        return new ResponseEntity<>(warehouseService.getWarehouse(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody Warehouse wareHouseRequest){
       return new ResponseEntity<>(warehouseService.createWareHouse(wareHouseRequest),HttpStatus.OK);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<?> show(@PathVariable("warehouseId") int warehouseId){
        return new ResponseEntity<>(warehouseService.getWareHouseById(warehouseId),HttpStatus.OK);
    }

    @DeleteMapping("/{warehouseId}")
    public String destroy(@PathVariable("warehouseId") int warehouseId){
        warehouseService.deleteWareHouseById(warehouseId);
        return "Warehouse has been deleted successfully";
    }
    @PutMapping("/{warehouseId}")
    public String update(@PathVariable int warehouseId, @RequestBody Warehouse request){
        warehouseService.updateWarehouse(warehouseId,request);
      return "Warehouse has been updated successfully";
    }



}
