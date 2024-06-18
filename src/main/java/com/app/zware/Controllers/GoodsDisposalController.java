package com.app.zware.Controllers;

import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Entities.User;
import com.app.zware.Entities.Warehouse;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.GoodsDisposalService;
import com.app.zware.Service.UserService;
import com.app.zware.Service.WarehouseService;
import com.app.zware.Validation.GoodsDisposalValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goodsdisposal")
public class GoodsDisposalController {

    @Autowired
    GoodsDisposalService goodsDisposalService;

    @Autowired
    GoodsDisposalValidator goodsDisposalValidator;

    @Autowired
    UserService userService;

    @Autowired
    WarehouseService warehouseService;

    @GetMapping("")
    public ResponseEntity<?> index() {
        //Authorization : ALL

        // response
        CustomResponse customResponse = new CustomResponse();
        customResponse.setAll(true,"Get data of all goods disposal success",goodsDisposalService.findAllGoods());
        return new ResponseEntity<>(customResponse,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody GoodsDisposal goods, HttpServletRequest request) {

        //response
        CustomResponse customResponse = new CustomResponse();

        //Authorization : Admin and user quan li kho do
        User user = userService.getRequestMaker(request);
        if(!user.getRole().equals("admin")&& !user.getWarehouse_id().equals(goods.getWarehouse_id())){
            customResponse.setAll(false,"You are not allowed",null);
            return new ResponseEntity<>(customResponse,HttpStatus.UNAUTHORIZED);
        }


        //Validation
        String message = goodsDisposalValidator.checkPost(goods);
        if(!message.isEmpty()){
            customResponse.setAll(false,message,null);
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }

            GoodsDisposal createdgoods = goodsDisposalService.createGoodsDisposed(goods);
            customResponse.setAll(true,"Goods Disposal created",createdgoods);
            return new ResponseEntity<>(customResponse,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        //Authorization : ALL

        //response
        CustomResponse customResponse = new CustomResponse();

       String message = goodsDisposalValidator.checkGet(id);
       if(!message.isEmpty()){
           customResponse.setAll(false,message,null);
           return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
       }

       customResponse.setAll(true,"get data of goods disposal with id "+id+" success",goodsDisposalService.getGoodsById(id));
       return new ResponseEntity<>(customResponse,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id,HttpServletRequest request) {

        //response
        CustomResponse customResponse = new CustomResponse();

        //Authorization : Admin and user quan ly kho do

        User user = userService.getRequestMaker(request);
        GoodsDisposal goods = goodsDisposalService.getGoodsById(id);
        if(!user.getRole().equals("admin")&&!user.getWarehouse_id().equals(goods.getWarehouse_id())){
            customResponse.setAll(false,"You are not allowed",null);
            return new ResponseEntity<>(customResponse,HttpStatus.UNAUTHORIZED);
        }

        String message = goodsDisposalValidator.checkDelete(id);
        if(!message.isEmpty()){
            customResponse.setAll(false,message,null);
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        }

            goodsDisposalService.deleteById(id);
            customResponse.setAll(true,"Goods Disposal with id "+id+" has been deleted",null);
            return new ResponseEntity<>(customResponse,HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GoodsDisposal requestGoods,HttpServletRequest request) {

        //response
        CustomResponse customResponse = new CustomResponse();

        //Authorization : Admin and user quan li kho do

        User user = userService.getRequestMaker(request);
        GoodsDisposal goods = goodsDisposalService.getGoodsById(id);
        if(!user.getRole().equals("admin")&&!user.getWarehouse_id().equals(goods.getWarehouse_id())){
            customResponse.setAll(false,"You are not allowed",null);
            return new ResponseEntity<>(customResponse,HttpStatus.UNAUTHORIZED);
        }
       GoodsDisposal mergedGoodsDisposal = goodsDisposalService.merge(id,requestGoods);
       String checkMessage = goodsDisposalValidator.checkPut(id,mergedGoodsDisposal);
       if(!checkMessage.isEmpty()){
           customResponse.setAll(false,checkMessage,null);
           return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
       }


       GoodsDisposal update = goodsDisposalService.update(mergedGoodsDisposal);
       customResponse.setAll(true,"Goods Disposal success",update);
       return new ResponseEntity<>(customResponse,HttpStatus.OK);
    }
}
