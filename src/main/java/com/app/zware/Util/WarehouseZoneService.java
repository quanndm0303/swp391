package com.app.zware.Util;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseZoneRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseZoneService {
    @Autowired
    WarehouseZoneRespository warehouseZoneRespository;

    public List<WarehouseZone> getAll(){
        return warehouseZoneRespository.findAll();
    }
}
