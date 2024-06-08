package com.app.zware.Validation;


import com.app.zware.Entities.OutboundTransaction;
import com.app.zware.Entities.Warehouse;
import com.app.zware.Repositories.OutboundTransactionRepository;
import com.app.zware.Repositories.UserRepository;
import com.app.zware.Repositories.WarehouseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutBoundTransactionValidator {
    @Autowired
    OutboundTransactionRepository outboundTransactionRepository;

    @Autowired
    WarehouseRespository warehouseRespository;

    @Autowired
    UserRepository userRepository;

    public String checkPost(OutboundTransaction outboundTransaction){
        if(outboundTransaction.getDate() == null){
            return "Date is invalid";
        }
        if( outboundTransaction.getStatus()== null || outboundTransaction.getStatus().isEmpty()){
            return "Status is invalid";
        }
        Integer destination = outboundTransaction.getDestination();
        if( destination == null || !warehouseRespository.existsById(outboundTransaction.getDestination())){
            return "Not found ID warehouse for destination";
        }

        Integer makerId = outboundTransaction.getMaker_id();
        if(makerId == null || !userRepository.existsById(outboundTransaction.getMaker_id())){
            return "Not found Maker";
        }
        return "";
    }

    public String checkPut(Integer id, OutboundTransaction outboundTransaction){
        if(id == null || !outboundTransactionRepository.existsById(id)) {
            return "Not found OutboundTransactionID";
        } else {
            return checkPost(outboundTransaction);
        }
    }

    public String checkGet(Integer id){
        if(!checkId(id)){
            return "Not found ID";
        } else {
            return "";
        }
    }

    public String checkDelete(Integer id){
        return checkGet(id);
    }

    public boolean checkId(Integer id){
        return outboundTransactionRepository.existsById(id);
    }
}
