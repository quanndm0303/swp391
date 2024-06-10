package com.app.zware.Controllers;

import com.app.zware.Entities.DisposedGood;
import com.app.zware.Service.DisposedGoodService;
import com.app.zware.Validation.DisposedGoodValidator;
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
    public ResponseEntity<?> store(@RequestBody DisposedGood disposedGood) {
       String checkMessage = disposedGoodValidator.checkPost(disposedGood);
       if(!checkMessage.isEmpty()){
           return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
       }
       disposedGoodService.createDisposedGood(disposedGood);
       return new ResponseEntity<>("Disposed has been created successfully",HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Integer id) {
      String checkMessage = disposedGoodValidator.checkGet(id);
      if(!checkMessage.isEmpty()){
          return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
      }

      return new ResponseEntity<>(disposedGoodService.getDisposedGoodById(id),HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") Integer id) {
       String checkMessgae = disposedGoodValidator.checkDelete(id);
           if(!checkMessgae.isEmpty()){
               return new ResponseEntity<>(checkMessgae,HttpStatus.BAD_REQUEST);
           }
           disposedGoodService.deleteDisposedGood(id);
           return new ResponseEntity<>("Disposal has been deleted successfully",HttpStatus.OK);
        }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody DisposedGood request) {
          DisposedGood mergeDisposedGood = disposedGoodService.merge(id,request);


          String checkMessage = disposedGoodValidator.checkPut(id,mergeDisposedGood);
            if(!checkMessage.isEmpty()){
                return new ResponseEntity<>(checkMessage,HttpStatus.BAD_REQUEST);
            }

          //update
        DisposedGood updated = disposedGoodService.update(mergeDisposedGood);

            return new ResponseEntity<>(updated,HttpStatus.OK);

    }
}
