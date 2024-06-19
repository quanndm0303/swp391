package com.app.zware.Controllers;

import com.app.zware.Entities.InboundTransaction;
import com.app.zware.Entities.InboundTransactionDetail;
import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.InboundTransactionDetailService;
import com.app.zware.Service.UserService;
import com.app.zware.Validation.InboundTransactionDetailValidator;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
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
@RequestMapping("/api/inbound_transaction_details")
public class InboundTransactionDetailController {

  @Autowired
  InboundTransactionDetailService service;

  @Autowired
  InboundTransactionDetailValidator validator;

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Validation: All
    //response
    CustomResponse customResponse = new CustomResponse();

    //Get
    customResponse.setAll(true,"Get data of all inbound transaction details success",service.getAll());
    return new ResponseEntity<>(customResponse,HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable Integer id) {
    // response
    CustomResponse customResponse = new CustomResponse();

    //Validation: All

    //VALIDATION
    String checkMessage = validator.checkGet(id);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false,checkMessage,null);
      return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
    }

    //GET

      customResponse.setAll(true,"get data of inbound transaction details with id "+id+" success",service.getById(id));
      return new ResponseEntity<>(customResponse, HttpStatus.OK);

  }

  @PostMapping("")

  public ResponseEntity<?> store(
      @RequestBody InboundTransactionDetail detail,
      HttpServletRequest request
  ) {
    //response
    CustomResponse customResponse = new CustomResponse();

    //Authorization: Admin or transaction maker
    User user = userService.getRequestMaker(request);
    InboundTransaction transaction = service.getTransaction(detail);
    if (!user.getRole().equals("admin") && !user.getId().equals(transaction.getMaker_id())) {
      customResponse.setAll(false,"You are nor allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //VALIDATION
    String checkMessage = validator.checkPost(detail);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false,checkMessage,null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    //Create
    //finally
    InboundTransactionDetail storedDetail = service.save(detail);
    customResponse.setAll(true,"Inbound transaction details created",storedDetail);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestBody InboundTransactionDetail detail,
      HttpServletRequest request
  ) {

    //response
    CustomResponse customResponse = new CustomResponse();

    //Merge
    InboundTransactionDetail mergedDetail = service.merge(id, detail);
    System.out.println(mergedDetail);

    //Authorization: Admin or transaction maker
    User user = userService.getRequestMaker(request);
    InboundTransaction transaction = service.getTransaction(mergedDetail);
    if (!user.getRole().equals("admin") && !user.getId().equals(transaction.getMaker_id())) {
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //VALIDATION
    String checkMessage = validator.checkPut(id, mergedDetail);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false,checkMessage,null);
      return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    //Update
    // finally
    InboundTransactionDetail updatedDetail = service.update(id, mergedDetail);
    customResponse.setAll(true,"Inbound transaction update success",updatedDetail);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> destroy(
      @PathVariable Integer id,
      HttpServletRequest request
  ) {
     //response
    CustomResponse customResponse = new CustomResponse();

    //Authorization: Admin
    User user = userService.getRequestMaker(request);
    if (!user.getRole().equals("admin")) {
      customResponse.setAll(false,"You are not allowed",null);
      return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
    }

    //VALIDATION
    String checkMessage = validator.checkDelete(id);
    if (!checkMessage.isEmpty()) {
      customResponse.setAll(false,checkMessage,null);
      return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
    }

    //DELETE
    // finally
    service.delete(id);
    customResponse.setAll(true,"Inbound transcation detail "+id+" has been deleted",null);
    return new ResponseEntity<>(customResponse, HttpStatus.OK);
  }

}
