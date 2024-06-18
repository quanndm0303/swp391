package com.app.zware.Repositories;

import com.app.zware.Entities.WarehouseZone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface WarehouseZoneRespository extends JpaRepository<WarehouseZone, Integer> {
     Optional<WarehouseZone> findByName(String name);

     @Query(
         value = "SELECT * FROM warehousezones z WHERE z.warehouse_id = ?1",
         nativeQuery = true)
     List<WarehouseZone> findByWarehouseId(Integer warehouseId);

}
