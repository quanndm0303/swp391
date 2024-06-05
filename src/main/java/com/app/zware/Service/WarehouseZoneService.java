package com.app.zware.Service;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseZoneRespository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseZoneService {

  @Autowired
  WarehouseZoneRespository warehouseZoneRespository;

  public List<WarehouseZone> getAll() {
    return warehouseZoneRespository.findAll();
  }

  public WarehouseZone createWarehouseZone(WarehouseZone request) {
    WarehouseZone warehouseZone = new WarehouseZone();
    warehouseZone.setName(request.getName());
    warehouseZone.setWarehouse_id(request.getWarehouse_id());
    return warehouseZoneRespository.save(warehouseZone);
  }

  public WarehouseZone getWarehouseZoneById(int id) {
    return warehouseZoneRespository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not Found WarehouseZone"));
  }

  public void deleteWarehouseZoneById(int id) {
    warehouseZoneRespository.deleteById(id);
  }

  public boolean checkIdExist(int id) {
    return warehouseZoneRespository.existsById(id);

  }


  public WarehouseZone merger(Integer oldWarehouseZoneId,WarehouseZone newWarehouseZone){
    WarehouseZone oldWarehouseZone = warehouseZoneRespository.findById(oldWarehouseZoneId).orElse(null);
    if(oldWarehouseZone==null){
      return null;
    }
    Optional.ofNullable(newWarehouseZone.getName()).ifPresent(oldWarehouseZone::setName);
    Optional.ofNullable(newWarehouseZone.getWarehouse_id()).ifPresent(oldWarehouseZone::setWarehouse_id);

    return oldWarehouseZone;
  }


  public WarehouseZone update(WarehouseZone mergedWarehouseZone){
   return warehouseZoneRespository.save(mergedWarehouseZone);
  }
}
