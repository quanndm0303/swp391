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

  public InboundTransactionDetail update(Integer id, InboundTransactionDetail request) {
    InboundTransactionDetail detail = repository.findById(id).orElse(null);
    if (detail == null) {
      return null;
    }

    Optional.ofNullable(request.getTransaction_id()).ifPresent(detail::setTransaction_id);
    Optional.of(request.getQuantity()).ifPresent(detail::setQuantity);
    Optional.ofNullable(request.getItem_id()).ifPresent(detail::setItem_id);
    Optional.ofNullable(request.getZone_id()).ifPresent(detail::setZone_id);

    return repository.save(detail);
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
