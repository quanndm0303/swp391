package com.app.zware.Util;

import com.app.zware.Entities.InboundTransaction;
import com.app.zware.Repositories.InboundTransactionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InboundTransactionService {

  @Autowired
  InboundTransactionRepository repository;

  public List<InboundTransaction> getAll() {
    return repository.findAll();
  }

  public InboundTransaction getById(int id) {
    return repository.findById(id).orElse(null);
  }

  public InboundTransaction save(InboundTransaction transaction) {
    repository.save(transaction);
    return transaction;
  }

  public InboundTransaction update(Integer id, InboundTransaction request) {
    InboundTransaction transaction = repository.findById(id).orElse(null);
    if (transaction == null) {
      return null;
    }

    Optional.ofNullable(request.getDate()).ifPresent(transaction::setDate);
    Optional.ofNullable(request.getMaker_id()).ifPresent(transaction::setMaker_id);
    Optional.ofNullable(request.getStatus()).ifPresent(transaction::setStatus);
    Optional.ofNullable(request.getSource()).ifPresent(transaction::setSource);

    return repository.save(transaction);
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
