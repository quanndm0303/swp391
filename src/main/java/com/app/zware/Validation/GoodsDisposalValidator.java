package com.app.zware.Validation;

import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Repositories.GoodsDisposalRepository;
import com.app.zware.Repositories.WarehouseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GoodsDisposalValidator {
    @Autowired
    GoodsDisposalRepository goodsDisposalRepository;

    @Autowired
    WarehouseRespository warehouseRespository;


    public String checkPost(GoodsDisposal goodsDisposal){
      if(goodsDisposal.getStatus().isEmpty()){
          return "Status is not empty";
      }
      if(goodsDisposal.getWarehouse_id()==null){
          return "Warehouse ID is not empty";
      }
      if (!checkWarehouseExist(goodsDisposal.getWarehouse_id())){
          return "Warehouse ID does not exist";
      }
      if(goodsDisposal.getDate()==null){
          return "Date is not empty";
      }
      if(goodsDisposal.getDate().after(new Date())){
          return "Date should not be in the future";
      }
      return "";
    }
    public String checkPut (GoodsDisposal goodsDisposal){
        return checkPost(goodsDisposal);
    }

    public String checkGet (Integer id){
        if(!checkIdExist(id)){
            return "Id is not valid";
        }
        return "";
    }
    public String checkDelete(Integer id){
        return checkGet(id);
    }


    private boolean checkIdExist(Integer id){
        return goodsDisposalRepository.existsById(id);
    }


    private boolean checkWarehouseExist(Integer id){
        return warehouseRespository.existsById(id);
    }
}
