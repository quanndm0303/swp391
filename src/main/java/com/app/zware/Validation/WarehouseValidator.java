package com.app.zware.Validation;

import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.WarehouseRespository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseValidator {

  @Autowired
  WarehouseRespository warehouseRespository;

  public String checkPost(Warehouse warehouse) {
    if (warehouse.getName().isEmpty()) {
      return "Warehouse name is not empty";
    }
    if (warehouse.getAddress().isEmpty()) {
      return "Warehouse address is not empty";
    }
    Optional<Warehouse> existingWarehouse = warehouseRespository.findByName(warehouse.getName());
    if (existingWarehouse.isPresent()) {
      return "Warehouse with the same name already exist";
    }
    return "";
  }

  public String checkPut(Integer warehouseId, Warehouse mergedWarehouse) {
    //Check id
    if (warehouseId == null || !warehouseRespository.existByIdAndIsDeletedFalse(warehouseId)) {
      return "Id is not valid";
    }

    //check info
    if (mergedWarehouse.getName().isEmpty()) {
      return "Warehouse name is not empty";
    }
    if (mergedWarehouse.getAddress().isEmpty()) {
      return "Warehouse address is not empty";
    }

    //check name : cannot same with other warehouse
    Warehouse existingWarehouse = warehouseRespository.findByName(mergedWarehouse.getName()).orElse(null);
    if (existingWarehouse == null){
        return "";
    } else{
      return (existingWarehouse.getId().equals(warehouseId)) ? "" : "Warehouse with the same name already exist";
    }
//    assert existingWarehouse != null;
//    if (!existingWarehouse.getId().equals(warehouseId)) {
//      return "Warehouse with the same name already exist";
//    } else{
//      return "";
//    }
  }

  private boolean checkWarehouseId(Integer id){
    return warehouseRespository.existByIdAndIsDeletedFalse(id);
  }

  public String checkGet(Integer id) {
    if (!checkWarehouseId(id)) {
      return "Id not valid";
    } else {
      return "";
    }
  }


  public String checkDetete(Integer id) {
    return checkGet(id);
  }

  private boolean checkIdExist(Integer id) {
    return warehouseRespository.existsById(id);
  }

}
