package com.app.zware.Validation;

import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.WarehouseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class WarehouseValidator {
    @Autowired
    WarehouseRespository warehouseRespository;

    public String checkPost(Warehouse warehouse){
      if (warehouse.getName().isEmpty()){
          return "Warehouse name is not empty";
      }
      if (warehouse.getAddress().isEmpty()){
          return "Warehouse address is not empty";
      }
        Optional<Warehouse> existingWarehouse = warehouseRespository.findByName(warehouse.getName());
        if (existingWarehouse.isPresent()){
            return "Warehouse with the same name already exist";
        }
        return "Validation successful";
    }

    public String checkPut(Warehouse warehouse){
        return checkPost(warehouse);
    }
    public String checkGet(Integer id){
        if(!checkIdExist(id)){
            return "Id not valid";
        }else {
            return "";
        }
    }
    public String checkDetete(Integer id){
        return checkGet(id);
    }
    private boolean checkIdExist( Integer id) {
        return warehouseRespository.existsById(id);
    }

}
