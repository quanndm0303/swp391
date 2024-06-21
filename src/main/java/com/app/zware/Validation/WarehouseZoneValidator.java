package com.app.zware.Validation;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseRespository;
import com.app.zware.Repositories.WarehouseZoneRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseZoneValidator {

  @Autowired
  WarehouseRespository warehouseRespository;

  @Autowired
  WarehouseZoneRespository zoneRespository;

  public String checkGet(Integer id) {
    if (id == null || !checkIdExist(id)) {
      return "Zone id is not valid";
    } else {
      return "";
    }
  }

  public String checkPost(WarehouseZone zone) {
    if (zone.getName() == null || zone.getName().isEmpty()) {
      return "Zone name cannot be empty";
    }
    if (zone.getWarehouse_id() == null) {
      return "Warehouse ID cannot be empty";
    }

    if (!checkWarehouseExist(zone.getWarehouse_id())) {
      return "Warehouse ID is not valid ";
    }

    WarehouseZone existingZone =
        zoneRespository.findByNameAndWarehouseId(zone.getName(), zone.getWarehouse_id());
    if (existingZone != null) {
      return "A WarehouseZone with the same name already exist";
    }

    return "";
  }


  public String checkPut(Integer warehouseZoneId, WarehouseZone mergedZone) {
    if (warehouseZoneId == null
        || !zoneRespository.existsByIdAndIsDeletedFalse(warehouseZoneId)) {
      return "Zone ID is not valid";
    }

    WarehouseZone existingZone = zoneRespository.findByNameAndWarehouseId(mergedZone.getName(),
        mergedZone.getWarehouse_id());

    if (existingZone != null
        && existingZone.getWarehouse_id().equals(mergedZone.getWarehouse_id())) {
      return "A zone with the same name already exist";
    }

    return "";

  }

  public String checkDelete(Integer id) {
    return checkGet(id);
  }

  private boolean checkIdExist(Integer id) {
    return zoneRespository.existsByIdAndIsDeletedFalse(id);
  }

  private boolean checkWarehouseExist(Integer warehouse_id) {
    return warehouseRespository.existByIdAndIsDeletedFalse(warehouse_id);
  }


}
