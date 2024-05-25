package com.app.zware.Repositories;

import com.app.zware.Entities.InboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundTransactionRepository extends JpaRepository<InboundTransaction, Integer> {

}
