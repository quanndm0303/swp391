package com.app.zware.Controllers;

import com.app.zware.Entities.Item;
import com.app.zware.Entities.Product;
import com.app.zware.Util.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("")
    public ResponseEntity<?> index(){
         List<Item> itemList = itemService.getAllItems();
         if(itemList.isEmpty()){
             return new ResponseEntity<>("List Items are empty!", HttpStatus.NOT_FOUND);
         }else {
             return new ResponseEntity<>(itemList,HttpStatus.OK);
         }
     }

     @PostMapping("")
    public ResponseEntity<?> store(@RequestBody Item item){
        return new ResponseEntity<>(itemService.createItem(item),HttpStatus.OK);
     }
     @GetMapping("/{itemId}")
        public ResponseEntity<?> show (@PathVariable("itemId") Integer itemId) {
         try {
             return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
         } catch (RuntimeException e) {
             return new ResponseEntity<>("Not Found Item", HttpStatus.NOT_FOUND);
         }
     }
     @DeleteMapping("/{itemId}")
       public ResponseEntity<?> destroy (@PathVariable("itemId") Integer itemId){
        if(!itemService.checkIdItemExist(itemId)){
            return new ResponseEntity<>("Not Found Item",HttpStatus.NOT_FOUND);
         }else {
            itemService.deleteItemById(itemId);
            return new ResponseEntity<>("Item has been deleted successfully",HttpStatus.OK);
        }
     }
     @PutMapping("/{itemId}")
    public ResponseEntity<?> update (@PathVariable("itemId") Integer itemId,@RequestBody Item request){
         if(!itemService.checkIdItemExist(itemId)){
             return new ResponseEntity<>("Not Found Item",HttpStatus.NOT_FOUND);
         }else {
             itemService.updateItem(itemId,request);
             return new ResponseEntity<>("Item has been updated successfully",HttpStatus.OK);
         }

     }






}

