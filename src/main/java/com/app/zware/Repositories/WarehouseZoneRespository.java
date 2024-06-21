package com.app.zware.Repositories;

import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Entities.WarehouseZone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface WarehouseZoneRespository extends JpaRepository<WarehouseZone, Integer> {
     @Query(value = "SELECT * FROM warehousezones i WHERE i.name =?1 AND i.isdeleted = 0", nativeQuery = true)
     WarehouseZone findByName(String name);

     @Query(
         value = "SELECT * FROM warehousezones z WHERE z.warehouse_id = ?1 AND z.isdeleted = 0",
         nativeQuery = true)
     List<WarehouseZone> findByWarehouseId(Integer warehouseId);

     @Query(value = "SELECT * FROM warehousezones i WHERE i.isdeleted = 0", nativeQuery = true)
     List<WarehouseZone> findAll();

     @Query(value = "SELECT * FROM warehousezones i WHERE i.id = ?1 AND i.isdeleted = 0", nativeQuery = true)
     Optional<WarehouseZone> findById(Integer id);

     @Query("SELECT CASE WHEN COUNT(id) > 0 THEN true ELSE false END FROM warehousezones i WHERE i.id = ?1 AND i.isdeleted = false")
     boolean existsByIdAndIsDeletedFalse(Integer id);
}
