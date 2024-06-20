package com.app.zware.Repositories;

import com.app.zware.Entities.InboundTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InboundTransactionDetailRepository extends JpaRepository<InboundTransactionDetail, Integer> {

    @Query(value = "select * from inboundtransactiondetails where transaction_id=?1",nativeQuery = true)
    List<InboundTransactionDetail> findByInboundTransactionId(Integer inboundTransactionId);
}
