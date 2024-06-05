package com.app.zware.Validation;

import com.app.zware.Entities.InboundTransactionDetail;
import com.app.zware.Repositories.InboundTransactionDetailRepository;
import com.app.zware.Repositories.InboundTransactionRepository;
import com.app.zware.Repositories.ItemRepository;
import com.app.zware.Repositories.WarehouseZoneRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InboundTransactionDetailValidator {

  @Autowired
  InboundTransactionDetailRepository ITD_Repo;

  @Autowired
  InboundTransactionRepository IT_Repo;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  WarehouseZoneRespository zoneRespository;

  public String checkGet(Integer id) {
    if (!ITD_Repo.existsById(id)) {
      return "Id is not valid";
    }
    return "";
  }

  public String checkPost(InboundTransactionDetail detail) {

    Integer id = detail.getTransaction_id();
    if (id == null || !IT_Repo.existsById(id)) {
      return "Transaction Id is not valid";
    }

    Integer itemId = detail.getItem_id();
    if (itemId == null || !itemRepository.existsById(itemId)) {
      return "Item Id is not valid";
    }

    Integer quantity = detail.getQuantity();
    if (quantity == null || quantity < 1) {
      return "Quantity is not valid";
    }

    //NEED TO CHECK MORE: Zone need to be in right warehouse...
    Integer zoneId = detail.getZone_id();
    if (zoneId == null || !zoneRespository.existsById(zoneId)) {
      return "Zone id is not valid";
    }

    return "";
  }

  public String checkPut(Integer id, InboundTransactionDetail detail) {
    if (id == null || !ITD_Repo.existsById(id)) {
      return "Id is not valid";
    }
    return checkPost(detail);
  }

  public String checkDelete(Integer id) {
    return checkGet(id);
  }

}
