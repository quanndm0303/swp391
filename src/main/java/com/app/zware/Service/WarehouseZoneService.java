package com.app.zware.Service;

import com.app.zware.Entities.WarehouseZone;
import com.app.zware.Repositories.WarehouseZoneRespository;
import java.util.List;
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

  public WarehouseZone updateWarehouseZone(int id, WarehouseZone request) {
    WarehouseZone warehouseZone = getWarehouseZoneById(id);
    if (request.getName() != null) {
      warehouseZone.setName(request.getName());
    }
    if (request.getWarehouse_id()!=null){
      warehouseZone.setWarehouse_id(request.getWarehouse_id());
    }
    return warehouseZoneRespository.save(warehouseZone);
  }
}
