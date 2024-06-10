package com.app.zware.Validation;

import com.app.zware.Entities.OutboundTransactionDetail;
import com.app.zware.Repositories.ItemRepository;
import com.app.zware.Repositories.OutboundTransactionDetailRepository;
import com.app.zware.Repositories.OutboundTransactionRepository;
import com.app.zware.Repositories.WarehouseZoneRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutboundTransactionDetailValidator {
    @Autowired
    OutboundTransactionDetailRepository outboundTransactionDetailRepository;

    @Autowired
    OutboundTransactionRepository outboundTransactionRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WarehouseZoneRespository warehouseZoneRespository;

    public String checkPost(OutboundTransactionDetail outboundTransactionDetail){
        Integer id = outboundTransactionDetail.getTransaction_id();
        if( id== null || !outboundTransactionRepository.existsById(outboundTransactionDetail.getTransaction_id())){
            return "Not found ID for Outbound Transactions";
        }
        Integer itemId = outboundTransactionDetail.getItem_id();
        if( itemId == null || !itemRepository.existsById(outboundTransactionDetail.getItem_id())){
            return "Not found itemID";
        }

        if(outboundTransactionDetail.getQuantity() <= 0 || String.valueOf(outboundTransactionDetail.getQuantity()).isBlank()){
            return "Quantity is invalid";
        }
        Integer zoneId = outboundTransactionDetail.getZone_id();
        if(zoneId == null || !warehouseZoneRespository.existsById(outboundTransactionDetail.getZone_id())){
            return "Not found ZoneID";
        }
        return "";
    }

    public String checkPut(Integer id, OutboundTransactionDetail outboundTransactionDetail){
        if( id == null || !outboundTransactionDetailRepository.existsById(id)){
            return "Not found ID";
        } else {
            return checkPost(outboundTransactionDetail);
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
        return outboundTransactionDetailRepository.existsById(id);
    }
}
