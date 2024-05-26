package com.app.zware.Controllers;

import com.app.zware.Entities.DisposedGood;
import com.app.zware.Service.DisposedGoodService;
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

    @GetMapping("")
    public ResponseEntity<?> index() {
        List<DisposedGood> disposedGoodList = disposedGoodService.getAllDisposedGood();
        if (disposedGoodList.isEmpty()) {
            return new ResponseEntity<>("Empty DisposedGood", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(disposedGoodList, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> store(@RequestBody DisposedGood disposedGood) {
        return new ResponseEntity<>(disposedGoodService.createDisposedGood(disposedGood), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
        try {
            DisposedGood disposedGood = disposedGoodService.getDisposedGoodById(id);
            return new ResponseEntity<>(disposedGood, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("DisposedGoods not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id) {
        if (!disposedGoodService.checkIdExist(id)) {
            return new ResponseEntity<>("Not Found DisposedGoods", HttpStatus.NOT_FOUND);
        } else {
            disposedGoodService.deleteDisposedGood(id);
            return new ResponseEntity<>("DisposedGoods has been deleted successfully", HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody DisposedGood request) {
        if (!disposedGoodService.checkIdExist(id)) {
            return new ResponseEntity<>("Not Found DisposedGoods", HttpStatus.NOT_FOUND);
        } else {
            DisposedGood disposedGood = disposedGoodService.updateDisposedGoodById(id, request);
            return new ResponseEntity<>(disposedGood, HttpStatus.OK);
        }
    }
}
