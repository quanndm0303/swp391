package com.app.zware.Controllers;

import com.app.zware.Entities.OutboundTransaction;
import com.app.zware.Service.OutboundTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/outbound_transactions")
public class OutboundTransactionController {
    @Autowired
    OutboundTransactionService outboundTransactionService;

    @GetMapping("")
    public ResponseEntity<?> index() {
        List<OutboundTransaction> outboundTransactionList = outboundTransactionService.getAllOutboundTransaction();
        if (outboundTransactionList.isEmpty()) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(outboundTransactionList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") int id) {
        try {
            OutboundTransaction outboundTransaction = new OutboundTransaction();
            return new ResponseEntity<>(outboundTransactionService.getOutboundTransactionById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Not Found ", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody OutboundTransaction request) {
        return new ResponseEntity<>(outboundTransactionService.createOutboundTransaction(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") int id) {
        if (!outboundTransactionService.checkIdExist(id)) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        } else {
            outboundTransactionService.deleteOutboundTransaction(id);
            return new ResponseEntity<>("OutboundTransaction have been deleted successfully", HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody OutboundTransaction request) {
        if (!outboundTransactionService.checkIdExist(id)) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        } else {
            outboundTransactionService.updateOutboundTransaction(id, request);
            return new ResponseEntity<>("OutboundTransaction have been updated successfully", HttpStatus.OK);
        }

    }
}
