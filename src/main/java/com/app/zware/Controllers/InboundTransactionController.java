package com.app.zware.Controllers;

import com.app.zware.Entities.InboundTransaction;
import com.app.zware.Entities.User;
import com.app.zware.Service.InboundTransactionService;
import com.app.zware.Service.UserService;
import com.app.zware.Validation.InboundTransactionValidator;
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
@RequestMapping("/api/inbound_transactions")
public class InboundTransactionController {

  @Autowired
  InboundTransactionService service;

  @Autowired
  InboundTransactionValidator validator;

  @Autowired
  UserService userService;

  @GetMapping("")
  public ResponseEntity<?> index() {
    //Validation: All authenticated user

    //GET
    List<InboundTransaction> transactions = service.getAll();
    if (transactions.isEmpty()) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable Integer id) {

    //Authorization: any authenticated user
    //Validate
    String checkMessage = validator.checkGet(id);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //Get
    InboundTransaction transaction = service.getById(id);
    if (transaction == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody InboundTransaction transaction) {
    //Authorization: any authenticated user
    //Validate
    String checkMessage = validator.checkPost(transaction);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //Save
    InboundTransaction storedTransaction = service.save(transaction);
    return new ResponseEntity<>(storedTransaction, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestBody InboundTransaction transaction,
      HttpServletRequest request
  ) {
    //Validation: Admin or Transaction's maker
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin") && !requestMaker.getId().equals(transaction.getId())) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //Merge info
    InboundTransaction mergedTransaction = service.merge(id, transaction);

    //Validate
    String checkMessage = validator.checkPut(id, mergedTransaction);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //Update
    InboundTransaction updatedTransaction = service.update(mergedTransaction);
    return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);

  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> destroy(
      @PathVariable Integer id,
      HttpServletRequest request
  ) {
    //Validation: Admin only
    User requestMaker = userService.getRequestMaker(request);
    if (!requestMaker.getRole().equals("admin")) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    //Validate
    String checkMessage = validator.checkDelete(id);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
    }

    //Delete
    service.delete(id);
    return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
  }
}
