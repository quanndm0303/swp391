package com.app.zware.Validation;

import com.app.zware.Entities.InboundTransaction;
import com.app.zware.Repositories.InboundTransactionRepository;
import com.app.zware.Repositories.OutboundTransactionRepository;
import com.app.zware.Repositories.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InboundTransactionValidator {

  @Autowired
  InboundTransactionRepository inboundTransactionRepository;

  @Autowired
  OutboundTransactionRepository outboundTransactionRepository;

  @Autowired
  UserRepository userRepository;

  public String checkGet(Integer id) {
    if (!inboundTransactionRepository.existsById(id)) {
      return "TransactionID is not valid";
    }
    return "";
  }

  public String checkPost(InboundTransaction inboundTransaction) {

    if (inboundTransaction.getDate() == null) {
      return "Transaction date is not valid";
    }

    Integer makerId = inboundTransaction.getMaker_id();
    if (makerId == null || !userRepository.existsById(makerId)) {
      return "Maker id is not valid";
    }

    List<String> statusList = Arrays.asList("pending", "processing", "done", "cancel");
    if (!statusList.contains(inboundTransaction.getStatus())) {
      return "Status is not valid";
    }

    Integer source = inboundTransaction.getSource();
    if (source != null && !outboundTransactionRepository.existsById(source)) {
      return "Source is not valid";
    }

    if(inboundTransaction.getExternal_source() == null){
      return "Extenral source is invalid";
    }

    return "";
  }

  public String checkPut(Integer transactionId, InboundTransaction transaction) {
    if (transactionId == null || !inboundTransactionRepository.existsById(transactionId)) {
      return "Id is not valid";
    }
    return checkPost(transaction);
  }

  public String checkDelete(Integer id) {
    return checkGet(id);
  }

}
