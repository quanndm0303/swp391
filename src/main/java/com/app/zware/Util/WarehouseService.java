package com.app.zware.Util;

import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.WarehouseRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRespository wareHouseRespository;

    public List<Warehouse> getWarehouse(){
        return wareHouseRespository.findAll();
    }
    public Warehouse createWareHouse(Warehouse request){
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        return wareHouseRespository.save(warehouse);
    }
    public Warehouse getWareHouseById(int id){
        return wareHouseRespository.findById(id).orElseThrow(()->new RuntimeException("Not Found WareHouse"));
    }
    public void deleteWareHouseById(int id){
        wareHouseRespository.deleteById(id);
    }
//    public Warehouse updateWareHouse(int id, Warehouse request){
//        Warehouse warehouse = getWareHouseById(id);
//        warehouse.setName(request.getName());
//        warehouse.setAddress(request.getAddress());
//       return wareHouseRespository.save(warehouse);
//
//    }
    public Warehouse updateWarehouse(int id, Warehouse request) {
        Warehouse warehouse = getWareHouseById(id);
        if(request.getName()!=null){
            warehouse.setName(request.getName());
        }
        if(request.getAddress()!=null){
            warehouse.setAddress(request.getAddress());
        }
        return  wareHouseRespository.save(warehouse);

    }

}