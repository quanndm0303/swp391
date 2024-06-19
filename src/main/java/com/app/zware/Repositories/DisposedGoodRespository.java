package com.app.zware.Repositories;

import com.app.zware.Entities.DisposedGood;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisposedGoodRespository extends JpaRepository<DisposedGood, Integer> {

    @Query(value = "select * from disposedgoods where disposal_id=?1 ",nativeQuery = true)
    List<DisposedGood> findByGoodDisposalId(Integer id);

}
