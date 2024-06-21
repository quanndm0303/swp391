package com.app.zware.Validation;

import com.app.zware.Entities.DisposedGood;
import com.app.zware.Repositories.DisposedGoodRespository;
import com.app.zware.Repositories.GoodsDisposalRepository;
import com.app.zware.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisposedGoodValidator {

  @Autowired
  DisposedGoodRespository disposedGoodRespository;

  @Autowired
  GoodsDisposalRepository goodsDisposalRepository;

  @Autowired
  ItemRepository itemRepository;

  public String checkPost(DisposedGood disposedGood) {
    if (disposedGood.getReason().isEmpty()) {
      return "Reason is not empty";
    }
    if (disposedGood.getQuantity() == null) {
      return "Quantity is not empty";
    }

    if (disposedGood.getQuantity() < 0) {
      return "Quantity must be greater than 0";
    }

    if (!checkItemExist(disposedGood.getItem_id())) {
      return "Item Id is not valid";
    }

    if (!checkGoodsDisposalExist(disposedGood.getDisposal_id())) {
      return "Disposal Id is not valid";
    }
    return "";
  }

  public String checkGet(Integer id) {
    if (!checkIdExist(id)) {
      return "Id is not valid";
    }
    return "";
  }

  public String checkDelete(Integer id) {
    return checkGet(id);
  }

  public String checkPut(Integer disposedId, DisposedGood disposedGood) {
    if (disposedGood == null || !disposedGoodRespository.existsById(disposedId)) {
      return "Id is not valid";
    }
    return checkPost(disposedGood);
  }

  private boolean checkItemExist(Integer id) {
    return itemRepository.existsById(id);
  }

  private boolean checkGoodsDisposalExist(Integer id) {
    return goodsDisposalRepository.existsById(id);
  }

  private boolean checkIdExist(Integer id) {
    return disposedGoodRespository.existsById(id);
  }


}

