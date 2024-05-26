package com.app.zware.Controllers;

import com.app.zware.Entities.InboundTransaction;
import com.app.zware.Entities.InboundTransactionDetail;
import com.app.zware.Service.InboundTransactionDetailService;
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
    InboundTransactionDetail detail = service.getById(id);
    if (detail == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(detail, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody InboundTransactionDetail detail) {
    InboundTransactionDetail storedDetail = service.save(detail);
    return new ResponseEntity<>(storedDetail, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Integer id,
      @RequestBody InboundTransactionDetail request) {
    InboundTransactionDetail detail = service.getById(id);
    if (detail == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      InboundTransactionDetail updatedDetail = service.update(id, request);
      return new ResponseEntity<>(updatedDetail, HttpStatus.OK);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> destroy(@PathVariable Integer id) {
    InboundTransactionDetail detail = service.getById(id);
    if (detail == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      service.delete(id);
      return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
    }
  }

}
