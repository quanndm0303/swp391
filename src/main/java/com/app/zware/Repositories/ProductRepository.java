package com.app.zware.Repositories;

import com.app.zware.Entities.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    Optional<Product> findByName(String file);

  @Query(value = "SELECT * FROM products p WHERE p.name = ?1 AND p.isdeleted = 0", nativeQuery = true)
  Optional<Product> findByName(String name);

  @Query(value = "SELECT * FROM products p WHERE p.isdeleted = 0", nativeQuery = true)
  List<Product> findAll();

  @Query(value = "SELECT * FROM products p WHERE p.id = ?1 AND p.isdeleted = 0", nativeQuery = true)
  Optional<Product> findById(Integer id);

  @Query("SELECT CASE WHEN COUNT(id) > 0 THEN true ELSE false END FROM products p WHERE p.id = ?1 AND p.isdeleted = false")
  boolean existsByIdAndIsDeletedFalse(Integer id);
}
