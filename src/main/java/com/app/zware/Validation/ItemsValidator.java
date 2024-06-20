package com.app.zware.Validation;

import com.app.zware.Entities.Item;
import com.app.zware.Repositories.ItemRepository;
import com.app.zware.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

        LocalDate currentDate = LocalDate.now();
        LocalDate expireDate = item.getExpire_date();

        // Kiểm tra xem expireDate có trước ngày hiện tại không
        if (expireDate.isBefore(currentDate)) {
            return "The date was not received in the past";
        }

        Item existItem =  itemRepository.findByProductIdAndExpiredDate(item.getProduct_id(), item.getExpire_date());
        System.out.println(existItem);
        if(existItem != null) {
            return "Information of Item was exist";
        }
        return "";
    }
    public String checkPut(Integer id, Item item) {
        if (id == null || !itemRepository.existsByIdAndIsDeletedFalse(id)) {
            return "Not found ID";
        }

        Item existItem = itemRepository.findByProductIdAndExpiredDate(item.getProduct_id(),item.getExpire_date());
        System.out.println(existItem);
        if (existItem == null) {
            if (id == null || !productRepository.existsByIdAndIsDeletedFalse(item.getProduct_id())) {
                return "Not found ProductID to add";
            }

            if (item.getExpire_date() == null) {
                return "Date not null";
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate expireDate = item.getExpire_date();

            // Kiểm tra xem expireDate có trước ngày hiện tại không
            if (expireDate.isBefore(currentDate)) {
                return "The date was not received in the past";
            }
        } else {
            if (!existItem.getId().equals(id)) {
                return "Information of Item was exist";
            }
            return "";
        }
        return "";
    }

    public String checkGet(Integer id){
        if(!itemRepository.existsByIdAndIsDeletedFalse(id)){
            return "Not found ID";
        }
        return "";
    }

    public String checkDelete(Integer id){
        return checkGet(id);
    }



}
