package com.app.zware.Validation;

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
        if(warehouseZone.getName() == null || warehouseZone.getName().isEmpty()){
            return "WarehouseZone Name is not empty";
        }
        if(warehouseZone.getWarehouse_id()==null || warehouseZone.getWarehouse_id().describeConstable().isEmpty()){
            return "Warehouse ID is not empty";
        }

        if(!checkWarehouseExist(warehouseZone.getWarehouse_id())) {
            return "Warehouse ID is not valid ";
        }

        WarehouseZone existingWarehouseZone = warehouseZoneRespository.findByName(warehouseZone.getName());
        if(existingWarehouseZone != null){
            return "WarehouseZone with the same name already exist";
        }
        return "";
    }
    private boolean checkWarehouseExist(Integer warehouse_id){

        return warehouseRespository.existByIdAndIsDeletedFalse(warehouse_id);
    }
    public String checkPut(Integer warehouseZoneId,WarehouseZone warehouseZone){
        System.out.println(warehouseZoneId);
        if(warehouseZoneId==null || !warehouseZoneRespository.existsByIdAndIsDeletedFalse(warehouseZoneId)){
            System.out.println(checkWarehouseExist(warehouseZoneId));
            return "Id is not valid";
        }
       WarehouseZone existWarehouseZone = warehouseZoneRespository.findByName(warehouseZone.getName());
        if(existWarehouseZone==null){
            return "";
        }else {
            return (existWarehouseZone.getId().equals(warehouseZoneId))? "":"WarehouseZone with the same name already exist";
        }



    }


    private boolean checkIdExist(Integer id){
      return  warehouseZoneRespository.existsByIdAndIsDeletedFalse(id);
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
