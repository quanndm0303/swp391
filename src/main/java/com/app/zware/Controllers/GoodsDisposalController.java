package com.app.zware.Controllers;

import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Entities.User;
import com.app.zware.Entities.Warehouse;
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

        List<GoodsDisposal> goodsDisposalList = goodsDisposalService.findAllGoods();
        if (goodsDisposalList.isEmpty()) {
            return new ResponseEntity<>("Empty GoodsDisposal", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(goodsDisposalList, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody GoodsDisposal goods, HttpServletRequest request) {
        //Authorization : Admin and user quan li kho do
        User user = userService.getRequestMaker(request);
        if(!user.getRole().equals("admin")&& !user.getWarehouse_id().equals(goods.getWarehouse_id())){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }


        //Validation
        String message = goodsDisposalValidator.checkPost(goods);
        if(!message.isEmpty()){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }else {
            goodsDisposalService.createGoodsDisposed(goods);
            return new ResponseEntity<>("GoodsDisposal has been created successfully",HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        //Authorization : ALL

       String message = goodsDisposalValidator.checkGet(id);
       if(!message.isEmpty()){
           return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
       }else {
           return new ResponseEntity<>(goodsDisposalService.getGoodsById(id),HttpStatus.OK);
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id,HttpServletRequest request) {
        //Authorization : Admin and user quan ly kho do

        User user = userService.getRequestMaker(request);
        GoodsDisposal goods = goodsDisposalService.getGoodsById(id);
        if(!user.getRole().equals("admin")&&!user.getWarehouse_id().equals(goods.getWarehouse_id())){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }

        String message = goodsDisposalValidator.checkDelete(id);
        if(!message.isEmpty()){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }else {
            goodsDisposalService.deleteById(id);
            return new ResponseEntity<>("GoodsDisposal has been deleted successfully",HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GoodsDisposal requestGoods,HttpServletRequest request) {
        //Authorization : Admin and user quan li kho do

        User user = userService.getRequestMaker(request);
        GoodsDisposal goods = goodsDisposalService.getGoodsById(id);
        if(!user.getRole().equals("admin")&&!user.getWarehouse_id().equals(goods.getWarehouse_id())){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }
       GoodsDisposal mergedGoodsDisposal = goodsDisposalService.merge(id,requestGoods);
       String checkMessage = goodsDisposalValidator.checkPut(id,mergedGoodsDisposal);
       if(!checkMessage.isEmpty()){
           return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
       }


       GoodsDisposal update = goodsDisposalService.update(mergedGoodsDisposal);
       return new ResponseEntity<>(update,HttpStatus.OK);
    }
}
