package com.app.zware.Repositories;

import com.app.zware.Entities.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query(value = "SELECT * FROM categories c WHERE c.name = ?1 AND c.isdeleted = 0", nativeQuery = true)
  Optional<Category> findByName(String name);

  @Query(value = "SELECT * FROM categories c WHERE c.isdeleted = false", nativeQuery = true)
  List<Category> findAll();

  @Query(value = "SELECT * FROM categories c WHERE c.id = ?1 AND c.isdeleted = false", nativeQuery = true)
  Optional<Category> findById(Integer id);

  @Query("SELECT CASE WHEN COUNT(id) > 0 THEN true ELSE false END FROM categories c WHERE c.id = ?1 AND c.isdeleted = false")
  boolean existsByIdAndIsDeletedFalse(Integer id);
}
