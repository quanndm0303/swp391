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
    WarehouseService wareHouseService;

    @GetMapping("")
    public ResponseEntity<?> getWareHouse(){
        return new ResponseEntity<>(wareHouseService.getWarehouse(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createWareHouse(@RequestBody Warehouse wareHouseRequest){
       return new ResponseEntity<>(wareHouseService.createWareHouse(wareHouseRequest),HttpStatus.OK);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<?> getWareHouseById(@PathVariable("warehouseId") int warehouseId){
        return new ResponseEntity<>(wareHouseService.getWareHouseById(warehouseId),HttpStatus.OK);
    }
    @DeleteMapping("/{warehouseId}")
    public String deleteWareHouseById(@PathVariable("warehouseId") int warehouseId){
        wareHouseService.deleteWareHouseById(warehouseId);
        return "Delete Warehouse succesful";
    }
    @PutMapping("/{warehouseId}")
    public String updateWareHouse(@PathVariable int warehouseId, @RequestBody Warehouse request){
      wareHouseService.updateWarehouse(warehouseId,request);
      return "Update succesful";

    }



}
