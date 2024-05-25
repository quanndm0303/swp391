package com.app.zware.Controllers;

import com.app.zware.Entities.InboundTransaction;
import com.app.zware.Util.InboundTransactionService;
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

  @GetMapping("")
  public ResponseEntity<?> index() {
    List<InboundTransaction> transactions = service.getAll();
    if (transactions.isEmpty()) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable Integer id) {
    InboundTransaction transaction = service.getById(id);
    if (transaction == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
  }

  @PostMapping("")
  public ResponseEntity<?> store(@RequestBody InboundTransaction transaction) {
    InboundTransaction storedTransaction = service.save(transaction);
    return new ResponseEntity<>(storedTransaction, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Integer id,
      @RequestBody InboundTransaction request) {
    InboundTransaction transaction = service.getById(id);
    if (transaction == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      InboundTransaction updatedTransaction = service.update(id, request);
      return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> destroy(@PathVariable Integer id) {
    InboundTransaction transaction = service.getById(id);
    if (transaction == null) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } else {
      service.delete(id);
      return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
    }
  }
}
