package com.app.zware.Controllers;

import com.app.zware.Entities.Warehouse;
import com.app.zware.RequestEntities.WareHouseRequest;
import com.app.zware.Util.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class WareHouseController {
    @Autowired
    WareHouseService wareHouseService;

    @GetMapping("/getwarehouse")
    public ResponseEntity<?> getWareHouse(){
        return new ResponseEntity<>(wareHouseService.getWarehouse(), HttpStatus.OK);
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createWareHouse(@RequestBody WareHouseRequest wareHouseRequest){
       return new ResponseEntity<>(wareHouseService.createWareHouse(wareHouseRequest),HttpStatus.OK);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<?> getWareHouseById(@PathVariable("warehouseId") int warehouseId){
        return new ResponseEntity<>(wareHouseService.getWareHouseById(warehouseId),HttpStatus.OK);
    }
    @DeleteMapping("/{warehouseId}")
    public void deleteWareHouseById(@PathVariable("warehouseId") int warehouseId){
        wareHouseService.deleteWareHouseById(warehouseId);
    }
    @PutMapping("/{warehouseId}")
    public ResponseEntity<?> updateWareHouse(@PathVariable int warehouseId, @RequestBody WareHouseRequest request){

       return new ResponseEntity<>(wareHouseService.updateWareHouse(warehouseId,request),HttpStatus.OK);
    }



}
