package com.app.zware.Service;

import com.app.zware.Entities.InboundTransactionDetail;
import com.app.zware.Repositories.InboundTransactionDetailRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InboundTransactionDetailService {

  @Autowired
  InboundTransactionDetailRepository repository;

  public List<InboundTransactionDetail> getAll() {
    return repository.findAll();
  }

  public InboundTransactionDetail getById(int id) {
    return repository.findById(id).orElse(null);
  }

  public InboundTransactionDetail save(InboundTransactionDetail detail) {
    repository.save(detail);
    return detail;
  }

  public InboundTransactionDetail update(Integer id, InboundTransactionDetail mergedDetail) {
    return repository.save(mergedDetail);
  }

  public InboundTransactionDetail merge(Integer oldDetailId, InboundTransactionDetail newDetail) {
    InboundTransactionDetail oldDetail = repository.findById(oldDetailId).orElse(null);
    if (oldDetail == null) {
      return null;
    }

    Optional.ofNullable(newDetail.getTransaction_id()).ifPresent(oldDetail::setTransaction_id);
    Optional.of(newDetail.getQuantity()).ifPresent(oldDetail::setQuantity);
    Optional.ofNullable(newDetail.getItem_id()).ifPresent(oldDetail::setItem_id);
    Optional.ofNullable(newDetail.getZone_id()).ifPresent(oldDetail::setZone_id);

    return oldDetail; //Has been UPDATED
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
