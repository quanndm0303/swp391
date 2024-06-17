package com.app.zware.Validation;

import com.app.zware.Entities.OutboundTransactionDetail;
import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Autowired
    WarehouseItemsRepository warehouseItemsRepository;

    public String checkPost(OutboundTransactionDetail outboundTransactionDetail){
        Integer id = outboundTransactionDetail.getTransaction_id();
        if( id== null || !outboundTransactionRepository.existsById(outboundTransactionDetail.getTransaction_id())){
            return "Not found ID for Outbound Transactions";
        }
        Integer item_id = outboundTransactionDetail.getItem_id();
        if( item_id == null || !itemRepository.existsById(outboundTransactionDetail.getItem_id())){
            return "Not found itemID";
        }

        if(outboundTransactionDetail.getQuantity() <= 0 || String.valueOf(outboundTransactionDetail.getQuantity()).isBlank()){
            return "Quantity is invalid";
        }
        Integer zone_id = outboundTransactionDetail.getZone_id();
        if(zone_id == null || !warehouseZoneRespository.existsById(outboundTransactionDetail.getZone_id())){
            return "Not found ZoneID";
        }

        WarehouseItems warehouseItem = warehouseItemsRepository.findByZoneIdAndItemId(zone_id, item_id);
        int availableQuantity = warehouseItem.getQuantity();
        if(outboundTransactionDetail.getQuantity() > availableQuantity){
            return "Quantity is not enough";
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
