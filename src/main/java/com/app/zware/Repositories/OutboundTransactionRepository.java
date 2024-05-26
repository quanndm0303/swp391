package com.app.zware.Repositories;

import com.app.zware.Entities.OutboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundTransactionRepository extends JpaRepository<OutboundTransaction,Integer> {
}
