package com.app.zware.Controllers;

import com.app.zware.Entities.OutboundTransaction;
import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.OutboundTransactionService;
import com.app.zware.Service.UserService;
import com.app.zware.Validation.OutBoundTransactionValidator;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> index() {
        //response
        CustomResponse customResponse = new CustomResponse();
        //Authorization: any authenticated user
        List<OutboundTransaction> outboundTransactionList = outboundTransactionService.getAllOutboundTransaction();
        if (outboundTransactionList.isEmpty()) {
            customResponse.setAll(false,"List are empty",null);
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        } else {
            customResponse.setAll(true,"Get data of all outboundTransaction success", outboundTransactionList);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        //response
        CustomResponse customResponse = new CustomResponse();
        //Authorization: any authenticated user
        //check validate
        String message = outBoundTransactionValidator.checkGet(id);

        if(!message.isEmpty()){
            //error
            customResponse.setAll(false,message,null);
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        } else {
            //get outbound transaction
            customResponse.setAll(true,"Get data of outboundTransaction with id: " + id + "success",
                    outboundTransactionService.getOutboundTransactionById(id));
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody OutboundTransaction request) {
        //response
        CustomResponse customResponse = new CustomResponse();
        //Authorization: any authenticated user
        //check validate
        String message = outBoundTransactionValidator.checkPost(request);

        if(!message.isEmpty()){
            //error
            customResponse.setAll(false,message,null);
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        } else {
            //create new outbound transaction
            customResponse.setAll(true,"OutboundTransaction has been created",
                    outboundTransactionService.createOutboundTransaction(request));
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id, HttpServletRequest request) {
        //response
        CustomResponse customResponse = new CustomResponse();
        //Validation: Admin
        User user = userService.getRequestMaker(request);
        if(!user.getRole().equals("admin")) {
            customResponse.setAll(false,"You are not allowed",null);
            return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
        }

        //check validate
        String message = outBoundTransactionValidator.checkDelete(id);

        if (!message.isEmpty()) {
            //error
            customResponse.setAll(false,message,null);
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        } else {
            //approve delete
            outboundTransactionService.deleteOutboundTransaction(id);
            customResponse.setAll(true,"OutboundTransaction with id: " + id + "has been deleted",null);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody OutboundTransaction request,
                                    HttpServletRequest userRequest) {
        //response
        CustomResponse customResponse = new CustomResponse();
        //Validation: Admin or Transaction's maker
        User user = userService.getRequestMaker(userRequest);
        if(!user.getRole().equals("admin") && !user.getId().equals(request.getMaker_id())) {
            customResponse.setAll(false,"You are not allowed",null);
            return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
        }

        //merge info
        OutboundTransaction mergedOutboundTransaction = outboundTransactionService.merge(id, request);

        //Validate
        String message = outBoundTransactionValidator.checkPut(id, request);
        if(!message.isEmpty()){
            customResponse.setAll(false,message,null);
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }

        // update
        OutboundTransaction updatedOutboundTransaction = outboundTransactionService.update(mergedOutboundTransaction);
        customResponse.setAll(true,"OutboundTransaction has been updated", updatedOutboundTransaction);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);

    }
}
