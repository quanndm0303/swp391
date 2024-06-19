package com.app.zware.Controllers;

import com.app.zware.Entities.Item;
import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.ItemService;
import java.util.List;

import com.app.zware.Service.UserService;
import com.app.zware.Validation.ItemsValidator;
import jakarta.servlet.http.HttpServletRequest;
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

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //response
    CustomResponse customResponse = new CustomResponse();
    //Authorization: All
    List<Item> itemList = itemService.getAllItems();
    if (itemList.isEmpty()) {
      customResponse.setAll(false,"List Items are empty!",null);
      return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
    } else {
      customResponse.setAll(true,"Get data of all Items success", itemList);
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
  }

  @GetMapping("/{itemId}")
  public ResponseEntity<?> show(@PathVariable("itemId") Integer itemId) {
    //response
    CustomResponse customResponse = new CustomResponse();
    //Authorization: All
    //check validate
    String message = itemsValidator.checkGet(itemId);

    if (!message.isEmpty()) {
      //error
      customResponse.setAll(false,message,null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }
    //approve get item
    customResponse.setAll(true,"Get data of Item with id: " + itemId+"success",itemService.getItemById(itemId));
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody Item item, HttpServletRequest request) {
    //response
    CustomResponse customResponse = new CustomResponse();
    //Authorization: Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }
    //check validate
    String message = itemsValidator.checkPost(item);

    if (!message.isEmpty()) {
      //error
      customResponse.setAll(false,message,null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }
    //approve create new item
    customResponse.setAll(true,"Item has been created",itemService.createItem(item));
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }


  @DeleteMapping("/{itemId}")
  public ResponseEntity<?> destroy(@PathVariable("itemId") Integer itemId, HttpServletRequest request) {
    //response
    CustomResponse customResponse = new CustomResponse();
    //Authorization: Admin
    User user = userService.getRequestMaker(request);
    if(!user.getRole().equals("admin")){
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //check validate
    String message = itemsValidator.checkDelete(itemId);


    if (!message.isEmpty()) {
      //error
      customResponse.setAll(false,message,null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }
    //approve delete item
    itemService.deleteItemById(itemId);
    customResponse.setAll(true,"Item with id:" + itemId +"has been deleted",null);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);


  }

  @PutMapping("/{itemId}")
  public ResponseEntity<?> update(@PathVariable("itemId") Integer itemId,
      @RequestBody Item request ,HttpServletRequest userRequest) {
    //response
    CustomResponse customResponse = new CustomResponse();
    //Authorization: Admin
    User user = userService.getRequestMaker(userRequest);
    if(!user.getRole().equals("admin")){
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

  //merge infor
    Item updatedItem = itemService.merge(itemId, request);

    // check validate
    String message = itemsValidator.checkPut(itemId,updatedItem);
    if (!message.isEmpty()) {
      customResponse.setAll(false,message,null);
      //error
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    } else {
      //approve update infor
      itemService.update(updatedItem);
      customResponse.setAll(true,"Item has been updated",updatedItem);
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

  }


}

