package com.app.zware.Service;

import com.app.zware.Entities.Item;
import com.app.zware.Entities.OutboundTransaction;
import com.app.zware.Repositories.OutboundTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutboundTransactionService {
    @Autowired
    OutboundTransactionRepository outboundTransactionRepository;

    public List<OutboundTransaction> getAllOutboundTransaction(){
        return outboundTransactionRepository.findAll();
    }
    public OutboundTransaction getOutboundTransactionById(int id){
        return outboundTransactionRepository.findById(id) .orElse(null);

    }
    public OutboundTransaction createOutboundTransaction(OutboundTransaction request){
        return outboundTransactionRepository.save(request);
    }

    public void deleteOutboundTransaction(Integer id){
        outboundTransactionRepository.deleteById(id);
    }

    public OutboundTransaction update(OutboundTransaction outboundTransaction){
        return outboundTransactionRepository.save(outboundTransaction);
    }

    public OutboundTransaction merge(Integer id,OutboundTransaction request){

       OutboundTransaction outboundTransaction = getOutboundTransactionById(id);
        if(outboundTransaction == null){
            return null;
        }
            Optional.ofNullable(request.getDate()).ifPresent(outboundTransaction::setDate);
            Optional.ofNullable(request.getMaker_id()).ifPresent(outboundTransaction::setMaker_id);
            Optional.ofNullable(request.getStatus()).ifPresent(outboundTransaction::setStatus);
            Optional.ofNullable(request.getDestination()).ifPresent(outboundTransaction::setDestination);

            return outboundTransaction;
        }

}
