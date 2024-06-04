package com.app.zware.Service;

import com.app.zware.Entities.OutboundTransactionDetail;
import com.app.zware.Repositories.OutboundTransactionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutboundTransactionDetailService {
    @Autowired
    OutboundTransactionDetailRepository outboundTransactionDetailRepository;

    public List<OutboundTransactionDetail> getAll(){
       return outboundTransactionDetailRepository.findAll();
    }
    public OutboundTransactionDetail create(OutboundTransactionDetail request){
        return outboundTransactionDetailRepository.save(request);
    }
    public boolean checkExistId(int id){
        return outboundTransactionDetailRepository.existsById(id);
    }
    public void delete(int id){
        outboundTransactionDetailRepository.deleteById(id);
    }
    public OutboundTransactionDetail getById(int id){
        return outboundTransactionDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found "));
    }
    public OutboundTransactionDetail update(int id,OutboundTransactionDetail request){
        OutboundTransactionDetail outboundTransactionDetail = getById(id);
        Optional.ofNullable(request.getTransaction_id()).ifPresent(outboundTransactionDetail::setTransaction_id);
        Optional.ofNullable(request.getItem_id()).ifPresent(outboundTransactionDetail::setItem_id);
        Optional.of(request.getQuantity()).ifPresent(outboundTransactionDetail::setQuantity);
        Optional.ofNullable(request.getZone_id()).ifPresent(outboundTransactionDetail::setZone_id);
       return  outboundTransactionDetailRepository.save(outboundTransactionDetail);
    }
}
