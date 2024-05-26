package com.app.zware.Controllers;


import com.app.zware.Entities.OutboundTransactionDetail;
import com.app.zware.Service.OutboundTransactionDetailService;
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
        try {
            return new ResponseEntity<>(outboundTransactionDetailService.getById(id),HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody OutboundTransactionDetail request){
        return new ResponseEntity<>(outboundTransactionDetailService.create(request),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy (@PathVariable("id") Integer id){
        if(!outboundTransactionDetailService.checkExistId(id)){
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }else {
            outboundTransactionDetailService.delete(id);
            return new ResponseEntity<>("OutboundTransactionDetail have been deleted successfully",HttpStatus.OK);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable int id,@RequestBody OutboundTransactionDetail request){
        if(!outboundTransactionDetailService.checkExistId(id)) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }else {
            outboundTransactionDetailService.update(id, request);
            return new ResponseEntity<>("OutboundTransactionDetail have been updated successfully",HttpStatus.OK);
        }
    }
}
