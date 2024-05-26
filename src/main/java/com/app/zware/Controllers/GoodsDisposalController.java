package com.app.zware.Controllers;

import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Service.GoodsDisposalService;
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

    @GetMapping("")
    public ResponseEntity<?> index() {
        List<GoodsDisposal> goodsDisposalList = goodsDisposalService.findAllGoods();
        if (goodsDisposalList.isEmpty()) {
            return new ResponseEntity<>("Empty GoodsDisposal", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(goodsDisposalList, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody GoodsDisposal goods) {
        return new ResponseEntity<>(goodsDisposalService.createGoodsDisposed(goods), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        try {
            GoodsDisposal goodsDisposal = goodsDisposalService.getGoodsById(id);
            return new ResponseEntity<>(goodsDisposal, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("GoodsDisposal not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id) {
        if (!goodsDisposalService.checkIdExist(id)) {
            return new ResponseEntity<>("Not Found GoodsDisposal", HttpStatus.NOT_FOUND);
        } else {
            goodsDisposalService.deleteById(id);
            return new ResponseEntity<>("GoodsDisposal has been deleted successfully", HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GoodsDisposal request) {
        if (!goodsDisposalService.checkIdExist(id)) {
            return new ResponseEntity<>("Not Found GoodsDisposal", HttpStatus.NOT_FOUND);
        } else {
            GoodsDisposal goodsDisposal = goodsDisposalService.update(id, request);
            return new ResponseEntity<>(goodsDisposal, HttpStatus.OK);
        }
    }
}
