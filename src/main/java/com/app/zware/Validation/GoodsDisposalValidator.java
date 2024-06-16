package com.app.zware.Validation;

import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Repositories.GoodsDisposalRepository;
import com.app.zware.Repositories.UserRepository;
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

    @Autowired
    UserRepository userRepository;


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
      if(goodsDisposal.getDate()==null) {
          return "Date is not empty";
      }

      Integer makerID = goodsDisposal.getMaker_id();
      if( makerID == null || !userRepository.existsById(makerID)){
          return "Not Found makerID";
      }


      return "";
    }
    public String checkPut (Integer goodsDisposalId,GoodsDisposal goodsDisposal){
        if(goodsDisposalId==null||!goodsDisposalRepository.existsById(goodsDisposalId)){
            return "Id is not valid";
        }
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
