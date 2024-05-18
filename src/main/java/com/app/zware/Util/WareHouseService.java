package com.app.zware.Util;

import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.WareHouseRespository;
import com.app.zware.RequestEntities.WareHouseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseService {
    @Autowired
    WareHouseRespository wareHouseRespository;

    public List<Warehouse> getWarehouse(){
        return wareHouseRespository.findAll();
    }
    public Warehouse createWareHouse(WareHouseRequest request){
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        return wareHouseRespository.save(warehouse);
    }
}
