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
}
