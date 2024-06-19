package com.app.zware.Validation;

import com.app.zware.Entities.Item;
import com.app.zware.Repositories.ItemRepository;
import com.app.zware.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ItemsValidator {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    public String checkPost(Item item){
        Integer id = item.getProduct_id();
        if(id == null || !productRepository.existsByIdAndIsDeletedFalse(item.getProduct_id())){
            return "Not found ProductID to add";
        }

        if(item.getExpire_date() == null){
            return "Date not null";
        }

        Date currentDate = new Date();

        // Kiểm tra xem expire_date có trước ngày hiện tại không
        if(item.getExpire_date().before(currentDate)){
            return "The date was not received in the past";
        }

        return "";
    }
    public String checkPut(Integer id, Item item){
        if( id == null || !itemRepository.existsById(id)){
            return "Not found ID";
        }
        return checkPost(item);
    }

    public String checkGet(Integer id){
        if(!itemRepository.existsById(id)){
            return "Not found ID";
        }
        return "";
    }

    public String checkDelete(Integer id){
        return checkGet(id);
    }


}
