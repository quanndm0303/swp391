package com.app.zware.Repositories;

import com.app.zware.Entities.WarehouseItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseItemsRepository extends JpaRepository<WarehouseItems,Integer> {
}
