package com.app.zware.Controllers;


import com.app.zware.Entities.OutboundTransactionDetail;
import com.app.zware.Service.OutboundTransactionDetailService;
import com.app.zware.Validation.OutboundTransactionDetailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/outbound_transaction_details")
public class OutboundTransactionDetailController {
    @Autowired
    OutboundTransactionDetailService outboundTransactionDetailService;

    @Autowired
    OutboundTransactionDetailValidator outboundTransactionDetailValidator;

    @GetMapping("")
    public ResponseEntity<?> index(){
        List<OutboundTransactionDetail> transactionDetailList = outboundTransactionDetailService.getAll();
        if(transactionDetailList.isEmpty()){
            return new ResponseEntity<>("List is empty!", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(transactionDetailList,HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id){
        //check validate
        String message = outboundTransactionDetailValidator.checkGet(id);

        if(!message.isEmpty()){
            //error
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } else {
            //approve get
            return new ResponseEntity<>(outboundTransactionDetailService.getById(id),HttpStatus.OK);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody OutboundTransactionDetail request){
        //check validate
        String message = outboundTransactionDetailValidator.checkPost(request);

        if(!message.isEmpty()){
            //error
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        } else {
            //create new outbound transactions details
            return new ResponseEntity<>(outboundTransactionDetailService.create(request),HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy (@PathVariable("id") Integer id){
        //check validate
        String message = outboundTransactionDetailValidator.checkDelete(id);

        if(!message.isEmpty()){
            //error
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }else {
            //approve delete
            outboundTransactionDetailService.delete(id);
            return new ResponseEntity<>("OutboundTransactionDetail have been deleted successfully",HttpStatus.OK);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable Integer id,@RequestBody OutboundTransactionDetail request){
        //merge info
        OutboundTransactionDetail updatedOutboundDetails = outboundTransactionDetailService.merge(id, request);

        //check validate
        String message = outboundTransactionDetailValidator.checkPut(id, updatedOutboundDetails);
        if(!message.isEmpty()) {
            //error
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }else {
            //approve updated
            outboundTransactionDetailService.update(updatedOutboundDetails);
            return new ResponseEntity<>(updatedOutboundDetails,HttpStatus.OK);
        }
    }
}
