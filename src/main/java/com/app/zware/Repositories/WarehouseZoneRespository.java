package com.app.zware.Repositories;

import com.app.zware.Entities.WarehouseZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseZoneRespository extends JpaRepository<WarehouseZone, Integer> {
     Optional<WarehouseZone> findByName(String name);
}
