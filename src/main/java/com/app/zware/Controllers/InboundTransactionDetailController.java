package com.app.zware.Controllers;

import com.app.zware.Entities.InboundTransactionDetail;
import com.app.zware.Service.InboundTransactionDetailService;
import com.app.zware.Validation.InboundTransactionDetailValidator;
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

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<InboundTransactionDetail> details = service.getAll();
    if (details.isEmpty()) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(details, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable Integer id) {
    //VALIDATION
    String checkMessage = validator.checkGet(id);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.OK);
    }

    //GET
    InboundTransactionDetail detail = service.getById(id);
    if (detail == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(detail, HttpStatus.OK);
    }
  }

  @PostMapping("")

  public ResponseEntity<?> store(@RequestBody InboundTransactionDetail detail) {

    //VALIDATION
    String checkMessage = validator.checkPost(detail);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.OK);
    }

    //Create
    InboundTransactionDetail storedDetail = service.save(detail);
    return new ResponseEntity<>(storedDetail, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Integer id,
      @RequestBody InboundTransactionDetail detail) {

    //Merge
    InboundTransactionDetail mergedDetail = service.merge(id, detail);

    //VALIDATION
    String checkMessage = validator.checkPut(id, mergedDetail);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.OK);
    }

    //Update
    InboundTransactionDetail updatedDetail = service.update(id, detail);
    return new ResponseEntity<>(updatedDetail, HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> destroy(@PathVariable Integer id) {

    //VALIDATION
    String checkMessage = validator.checkDelete(id);
    if (!checkMessage.isEmpty()) {
      return new ResponseEntity<>(checkMessage, HttpStatus.OK);
    }

    //DELETE
    service.delete(id);
    return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
  }

}
