package com.app.zware.Repositories;

import com.app.zware.Entities.WarehouseZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface WarehouseZoneRespository extends JpaRepository<WarehouseZone, Integer> {
     Optional<WarehouseZone> findByName(String name);


}
