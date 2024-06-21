package com.app.zware.Repositories;

import com.app.zware.Entities.InboundTransactionDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundTransactionDetailRepository extends
    JpaRepository<InboundTransactionDetail, Integer> {


  @Query(value = "SELECT itd.* FROM InboundTransactionDetails itd " +
      "INNER JOIN InboundTransactions it ON itd.transaction_id = it.id " +
      "WHERE it.id = :inboundTransactionId AND it.isdeleted = false",
      nativeQuery = true)
  List<InboundTransactionDetail> findByInboundTransactionId(
      @Param("inboundTransactionId") Integer inboundTransactionId);
}
