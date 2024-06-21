package com.app.zware.Repositories;

import com.app.zware.Entities.DisposedGood;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisposedGoodRespository extends JpaRepository<DisposedGood, Integer> {


    @Query(value = "SELECT dg.* FROM DisposedGoods dg " +
            "INNER JOIN GoodsDisposal gd ON dg.disposal_id = gd.id " +
            "WHERE gd.id = :disposalId AND gd.isdeleted = FALSE",
            nativeQuery = true)
    List<DisposedGood> findByGoodDisposalId(@Param("disposalId") Integer disposalId);

}
