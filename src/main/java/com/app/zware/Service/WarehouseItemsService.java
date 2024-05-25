package com.app.zware.Service;

import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Repositories.WarehouseItemsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseItemsService {

  @Autowired
  WarehouseItemsRepository warehouseItemsRepository;

  public List<WarehouseItems> getAllWarehouseItems() {
    return warehouseItemsRepository.findAll();
  }

  public WarehouseItems getById(int id) {
    return warehouseItemsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not Found WareHouseItems"));
  }

  public WarehouseItems createWarehouseItems(WarehouseItems request) {
    return warehouseItemsRepository.save(request);
  }

  public void deleteWarehouseItemsById(int id) {
    warehouseItemsRepository.deleteById(id);
  }

  public Boolean checkWarehouseItemsId(int id) {
    return warehouseItemsRepository.existsById(id);
  }

  public WarehouseItems updateWarehouseItemsById(int id, WarehouseItems request) {
    WarehouseItems warehouseItems = getById(id);

    Optional.of(request.getItem_id()).ifPresent(warehouseItems::setItem_id);
    Optional.of(request.getQuantity()).ifPresent(warehouseItems::setQuantity);
    Optional.of(request.getZone_id()).ifPresent(warehouseItems::setZone_id);

    return warehouseItemsRepository.save(warehouseItems);
  }

}
