package com.app.zware.Service;

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

  public InboundTransaction update(InboundTransaction mergedTransaction) {
    return repository.save(mergedTransaction);
  }

  public InboundTransaction merge(Integer oldTransactionId, InboundTransaction newTransaction){
    InboundTransaction oldTransaction = repository.findById(oldTransactionId).orElse(null);
    if (oldTransaction == null){
      return null;
    }

    Optional.ofNullable(newTransaction.getDate()).ifPresent(oldTransaction::setDate);
    Optional.ofNullable(newTransaction.getMaker_id()).ifPresent(oldTransaction::setMaker_id);
    Optional.ofNullable(newTransaction.getStatus()).ifPresent(oldTransaction::setStatus);
    Optional.ofNullable(newTransaction.getSource()).ifPresent(oldTransaction::setSource);
    Optional.ofNullable(newTransaction.getExternal_source()).ifPresent(oldTransaction::setExternal_source);

    return oldTransaction; //has been UPDATED
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
