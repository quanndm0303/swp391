package com.app.zware.Controllers;

import com.app.zware.Entities.DisposedGood;
import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Entities.User;
import com.app.zware.Service.DisposedGoodService;
import com.app.zware.Service.GoodsDisposalService;
import com.app.zware.Service.UserService;
import com.app.zware.Validation.DisposedGoodValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disposedgood")
public class DisposedGoodController {

    @Autowired
    DisposedGoodService disposedGoodService;

    @Autowired
    DisposedGoodValidator disposedGoodValidator;

    @Autowired
    UserService userService;

    @Autowired
    GoodsDisposalService goodsDisposalService;

    @GetMapping("")
    public ResponseEntity<?> index() {
        //Authorization : ALL

        List<DisposedGood> disposedGoodList = disposedGoodService.getAllDisposedGood();
        if (disposedGoodList.isEmpty()) {
            return new ResponseEntity<>("Empty DisposedGood", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(disposedGoodList, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody DisposedGood newDisposedGood, HttpServletRequest request) {
        //Authorization : Admin and manager warehouse
        User userRequestMaker = userService.getRequestMaker(request);


        GoodsDisposal goodsDisposal = goodsDisposalService.getGoodsById(newDisposedGood.getDisposal_id());
        //Authorization
        if(!userRequestMaker.getRole().equals("admin")&& !userRequestMaker.getWarehouse_id().equals(goodsDisposal.getWarehouse_id())){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }


        // validation
       String checkMessage = disposedGoodValidator.checkPost(newDisposedGood);
       if(!checkMessage.isEmpty()){
           return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
       }
       disposedGoodService.createDisposedGood(newDisposedGood);
       return new ResponseEntity<>("Disposed has been created successfully",HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        //Authorization : ALL

      String checkMessage = disposedGoodValidator.checkGet(id);
      if(!checkMessage.isEmpty()){
          return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
      }

      return new ResponseEntity<>(disposedGoodService.getDisposedGoodById(id),HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id,HttpServletRequest request) {
        //Authorization : Admin and manager

        DisposedGood disposedGood = disposedGoodService.getDisposedGoodById(id);
        if(disposedGood==null){
            return new ResponseEntity<>("Disposed Good is null",HttpStatus.NOT_FOUND);
        }
        GoodsDisposal goodsDisposal = goodsDisposalService.getGoodsById(disposedGood.getDisposal_id());

        User userRequestMaker = userService.getRequestMaker(request);
        if(!userRequestMaker.getRole().equals("admin")&&!userRequestMaker.getWarehouse_id().equals(goodsDisposal.getWarehouse_id())){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }


       String checkMessgae = disposedGoodValidator.checkDelete(id);
           if(!checkMessgae.isEmpty()){
               return new ResponseEntity<>(checkMessgae,HttpStatus.BAD_REQUEST);
           }
           disposedGoodService.deleteDisposedGood(id);
           return new ResponseEntity<>("Disposal has been deleted successfully",HttpStatus.OK);
        }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody DisposedGood requestDisposedGood,HttpServletRequest request) {
        //Authorization : Admin and manager

        DisposedGood disposedGood = disposedGoodService.getDisposedGoodById(id);
        if(disposedGood==null){
            return new ResponseEntity<>("Disposed Good is null",HttpStatus.NOT_FOUND);
        }
        GoodsDisposal goodsDisposal = goodsDisposalService.getGoodsById(disposedGood.getDisposal_id());

        User userRequestMaker = userService.getRequestMaker(request);
        if(!userRequestMaker.getRole().equals("admin")&&!userRequestMaker.getWarehouse_id().equals(goodsDisposal.getWarehouse_id())){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }


          DisposedGood mergeDisposedGood = disposedGoodService.merge(id,requestDisposedGood);


          String checkMessage = disposedGoodValidator.checkPut(id,mergeDisposedGood);
            if(!checkMessage.isEmpty()){
                return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
            }

          //update
        DisposedGood updated = disposedGoodService.update(mergeDisposedGood);

            return new ResponseEntity<>(updated,HttpStatus.OK);

    }
}
