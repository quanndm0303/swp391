package com.app.zware.Controllers;

import com.app.zware.Entities.OutboundTransaction;
import com.app.zware.Service.OutboundTransactionService;
import com.app.zware.Validation.OutBoundTransactionValidator;
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

    @Autowired
    OutBoundTransactionValidator outBoundTransactionValidator;

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
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        //check validate
        String message = outBoundTransactionValidator.checkGet(id);

        if(!message.isEmpty()){
            //error
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } else {
            //get outbound transaction
            return new ResponseEntity<>(outboundTransactionService.getOutboundTransactionById(id), HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody OutboundTransaction request) {
        //check validate
        String message = outBoundTransactionValidator.checkPost(request);

        if(!message.isEmpty()){
            //error
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            //create new outbound transaction
            return new ResponseEntity<>(outboundTransactionService.createOutboundTransaction(request), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id) {
        //check validate
        String message = outBoundTransactionValidator.checkDelete(id);

        if (!message.isEmpty()) {
            //error
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            //approve delete
            outboundTransactionService.deleteOutboundTransaction(id);
            return new ResponseEntity<>("OutboundTransaction have been deleted successfully", HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody OutboundTransaction request) {
        //merge info
        OutboundTransaction mergedOutboundTransaction = outboundTransactionService.merge(id, request);

        //Validate
        String message = outBoundTransactionValidator.checkPut(id, request);
        if(!message.isEmpty()){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        // update
        OutboundTransaction updatedOutboundTransaction = outboundTransactionService.update(mergedOutboundTransaction);

        return new ResponseEntity<>(updatedOutboundTransaction, HttpStatus.OK);

    }
}
