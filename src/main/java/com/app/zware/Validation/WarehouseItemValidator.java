package com.app.zware.Validation;

import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Repositories.ItemRepository;
import com.app.zware.Repositories.WarehouseItemsRepository;
import com.app.zware.Repositories.WarehouseZoneRespository;
import com.app.zware.Service.WarehouseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseItemValidator {


    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WarehouseZoneRespository warehouseZoneRespository;

    @Autowired
    WarehouseItemsRepository warehouseItemsRepository;

    @Autowired
    WarehouseItemsService warehouseItemsService;

    public String checkPost(WarehouseItems warehouseItems){

         if(warehouseItems.getItem_id()==null){
             return "Item Id is not empty";
         }

         if(warehouseItems.getZone_id()==null){
             return "Zone Id is not empty";
         }

          if(!checkItemExist(warehouseItems.getItem_id())){
              return "Item is not valid";
          }

          if(!checkWarehouseZoneExist(warehouseItems.getZone_id())){
              return "WarehouseZone is not valid";
          }

          if(warehouseItems.getQuantity()==null){
              return "Quantity is not empty";
          }

          if(warehouseItems.getQuantity()<0){
              return "Quantity is not valid";
          }
          WarehouseItems existWarehouseItem = warehouseItemsRepository.findByZoneIdAndItemId(warehouseItems.getZone_id(), warehouseItems.getItem_id());
          if(existWarehouseItem != null){
              warehouseItemsService.addQuantityToExistWarehouseItem(existWarehouseItem.getId(), warehouseItems);
              return "ZoneId and ItemId were exist so quantity was added to old WarehouseItem";
          }
          return "";
    }

    public String checkPut(Integer warehouseItemId,WarehouseItems warehouseItems){
        if(warehouseItemId==null||!warehouseItemsRepository.existsByIdAndIsDeletedFalse(warehouseItemId)){
            return "Id is not found";
        }

        if(warehouseItems.getItem_id()==null){
            return "Item Id is not empty";
        }

        if(warehouseItems.getZone_id()==null){
            return "Zone Id is not empty";
        }

        if(!checkItemExist(warehouseItems.getItem_id())){
            return "Item is not valid";
        }

        if(!checkWarehouseZoneExist(warehouseItems.getZone_id())){
            return "WarehouseZone is not valid";
        }

        if(warehouseItems.getQuantity()==null){
            return "Quantity is not empty";
        }

        if(warehouseItems.getQuantity()<0){
            return "Quantity is not valid";
        }

        WarehouseItems existWarehouseItem = warehouseItemsRepository.findByZoneIdAndItemId(warehouseItems.getZone_id(), warehouseItems.getItem_id());
        System.out.println(existWarehouseItem);
        if(existWarehouseItem != null && !existWarehouseItem.getId().equals(warehouseItemId)){
            warehouseItemsService.addQuantityToExistWarehouseItem(existWarehouseItem.getId(), warehouseItems);
            warehouseItemsService.deleteWarehouseItemsById(warehouseItemId);
            return "ZoneId and ItemId were exist so quantity was added to old WarehouseItem and WarehouseItem updated was deleted";
        }
        return "";
    }

    public String checkGet(Integer id){
        if(!checkIdExist(id)||id==null){
            return "Id is not found";
        }

        return "";
    }

    public String checkDelete(Integer id){
        return checkGet(id);
    }




    private boolean checkWarehouseZoneExist(Integer id){
        return warehouseZoneRespository.existsByIdAndIsDeletedFalse(id);
    }


    private boolean checkItemExist(Integer id){
        return itemRepository.existsByIdAndIsDeletedFalse(id);
    }

    private boolean checkIdExist(Integer id){
        return warehouseItemsRepository.existsByIdAndIsDeletedFalse(id);
    }
}
