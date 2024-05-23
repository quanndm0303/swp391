package com.app.zware.Controllers;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseZoneRespository;
import com.app.zware.Util.WarehouseService;
import com.app.zware.Util.WarehouseZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehousezones")
public class WarehouseZoneController {
    @Autowired
    WarehouseZoneService warehouseZoneService;

    @GetMapping("")
    public ResponseEntity<?> show (){
        List<WarehouseZone> warehouseZones = warehouseZoneService.getAll();

        if (warehouseZones.isEmpty()) {
            return new ResponseEntity<>("Empty WarehouseZones", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(warehouseZoneService.getAll(), HttpStatus.OK);
    }

}
