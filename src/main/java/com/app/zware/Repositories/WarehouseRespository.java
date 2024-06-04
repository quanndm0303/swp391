package com.app.zware.Repositories;

import com.app.zware.Entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRespository extends JpaRepository<Warehouse, Integer> {
     Optional<Warehouse> findByName(String name);
}
