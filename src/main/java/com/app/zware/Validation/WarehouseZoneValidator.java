package com.app.zware.Validation;

import com.app.zware.Entities.Warehouse;
import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseRespository;
import com.app.zware.Repositories.WarehouseZoneRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseZoneValidator {

    @Autowired
    WarehouseRespository warehouseRespository;

    @Autowired
    WarehouseZoneRespository warehouseZoneRespository;

    public String checkPost(WarehouseZone warehouseZone){
        if(warehouseZone.getName().isEmpty()){
            return "WarehouseZone Name is not empty";
        }
        if(warehouseZone.getWarehouse_id()==null){
            return "Warehouse ID is not empty";
        }

        if(!checkWarehouseExist(warehouseZone.getWarehouse_id())){
            return "Warehouse ID is not valid ";
        }

        Optional<WarehouseZone> existingWarehouseZone = warehouseZoneRespository.findByName(warehouseZone.getName());
        if(existingWarehouseZone.isPresent()){
            return "Warehouse Zone with the same name already ";
        }
        return "Validation successful";
    }
    private boolean checkWarehouseExist(Integer warehouse_id){
        return warehouseRespository.existsById(warehouse_id);
    }
    public String checkPut(WarehouseZone warehouseZone){
        return checkPost(warehouseZone);
    }
    private boolean checkIdExist(Integer id){
      return  warehouseZoneRespository.existsById(id);
    }
    public String checkGet(Integer id){
        if(!checkIdExist(id)){
            return "Id not valid";
        }
        else {
            return "";
        }
    }
    public String checkDelete(Integer id){
        return checkGet(id);
    }
}
