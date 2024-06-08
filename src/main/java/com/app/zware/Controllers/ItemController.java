package com.app.zware.Controllers;

import com.app.zware.Entities.Item;
import com.app.zware.Service.ItemService;
import java.util.List;

import com.app.zware.Validation.ItemsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

  @Autowired
  ItemService itemService;

  @Autowired
  ItemsValidator itemsValidator;

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<Item> itemList = itemService.getAllItems();
    if (itemList.isEmpty()) {
      return new ResponseEntity<>("List Items are empty!", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
  }

  @GetMapping("/{itemId}")
  public ResponseEntity<?> show(@PathVariable("itemId") Integer itemId) {
    //check validate
    String message = itemsValidator.checkGet(itemId);

    if (!message.isEmpty()) {
      //error
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    //approve get item
    return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Item item) {
    //check validate
    String message = itemsValidator.checkPost(item);

    if (!message.isEmpty()) {
      //error
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    //approve create new item
    return new ResponseEntity<>(itemService.createItem(item), HttpStatus.OK);
  }


  @DeleteMapping("/{itemId}")
  public ResponseEntity<?> destroy(@PathVariable("itemId") Integer itemId) {
    //check validate
    String message = itemsValidator.checkDelete(itemId);


    if (!message.isEmpty()) {
      //error
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    //approve delete item
    itemService.deleteItemById(itemId);
    return new ResponseEntity<>("Item has been deleted successfully", HttpStatus.OK);


  }

  @PutMapping("/{itemId}")
  public ResponseEntity<?> update(@PathVariable("itemId") Integer itemId,
      @RequestBody Item request) {
  //merge infor
    Item updatedItem = itemService.merge(itemId, request);

    // check validate
    String message = itemsValidator.checkPut(itemId,updatedItem);
    if (!message.isEmpty()) {
      //error
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } else {
      //approve update infor
      itemService.update(updatedItem);
      return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

  }


}

