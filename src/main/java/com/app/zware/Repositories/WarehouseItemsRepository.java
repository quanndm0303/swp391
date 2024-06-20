package com.app.zware.Repositories;

import com.app.zware.Entities.WarehouseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseItemsRepository extends JpaRepository<WarehouseItems, Integer> {
//    Optional<WarehouseItems> findByZoneIdAndItemId(Integer zone_id, Integer item_id);
    @Query(value = "SELECT w FROM warehouseitems w WHERE w.zone_id = :zoneId and w.item_id = :itemId")
    WarehouseItems findByZoneIdAndItemId(@Param("zoneId") Integer zoneId, @Param("itemId") Integer itemId);


    @Query(value = "SELECT wi.* " +
            "FROM WarehouseItems wi " +
            "JOIN WarehouseZones wz ON wi.zone_id = wz.id " +
            "WHERE wz.warehouse_id = :warehouseId", nativeQuery = true)
    List<WarehouseItems> findWarehouseItemByWarehouseId(Integer warehouseId);
}
