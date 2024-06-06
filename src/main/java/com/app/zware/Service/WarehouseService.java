package com.app.zware.Service;

import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.WarehouseRespository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

  @Autowired
  WarehouseRespository wareHouseRespository;

  public List<Warehouse> getWarehouse() {
    return wareHouseRespository.findAll();
  }

  public Warehouse createWareHouse(Warehouse request) {
    Warehouse warehouse = new Warehouse();
    warehouse.setName(request.getName());
    warehouse.setAddress(request.getAddress());
    return wareHouseRespository.save(warehouse);
  }

  public Warehouse getWareHouseById(int id) {
    return wareHouseRespository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not Found WareHouse"));
  }

  public void deleteWareHouseById(int id) {
    wareHouseRespository.deleteById(id);
  }

  public Warehouse merge(Integer oldWarehouseId,Warehouse newWarehouse){
    Warehouse oldWarehouse = wareHouseRespository.findById(oldWarehouseId).orElse(null);
    if(oldWarehouse == null){
      return null;
    }

    Optional.ofNullable(newWarehouse.getName()).ifPresent(oldWarehouse::setName);
    Optional.ofNullable(newWarehouse.getAddress()).ifPresent(oldWarehouse::setAddress);

    return oldWarehouse;
  }


  public Warehouse updateWarehouse(Warehouse mergedWarehouse) {
    return wareHouseRespository.save(mergedWarehouse);

  }

}
