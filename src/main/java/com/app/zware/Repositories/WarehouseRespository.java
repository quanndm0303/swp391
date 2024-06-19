package com.app.zware.Repositories;

import com.app.zware.Entities.Warehouse;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface WarehouseRespository extends JpaRepository<Warehouse, Integer> {

     @Query(value = "select * from warehouses  where isdeleted=false",nativeQuery = true)
     List<Warehouse> findAll();

     @Query(value = "select * from warehouses where id=?1 and isdeleted=false",nativeQuery = true)
     Optional<Warehouse> findById(Integer id);

     @Query(value = "select * from warehouses where name=?1 and isdeleted=false",nativeQuery = true)
     Optional<Warehouse> findByName(String name);

     @Query("select case when COUNT(id)>0 THEN true ELSE false END FROM warehouses w where w.id=?1 and w.isdeleted = false")
     boolean existByIdAndIsDeletedFalse(Integer id);




}
