package com.app.zware.Service;

import com.app.zware.Entities.DisposedGood;
import com.app.zware.Repositories.DisposedGoodRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisposedGoodService {

    @Autowired
    DisposedGoodRespository disposedGoodRespository;

    public List<DisposedGood> getAllDisposedGood(){
        return disposedGoodRespository.findAll();
    }

    public DisposedGood getDisposedGoodById(Integer id){
        return disposedGoodRespository.findById(id).orElseThrow(() -> new RuntimeException("Not Found DisposedGood"));
    }

    public DisposedGood createDisposedGood(DisposedGood request){
        return disposedGoodRespository.save(request);
    }

    public void deleteDisposedGood(Integer id){
        disposedGoodRespository.deleteById(id);
    }

    public Boolean checkIdExist(Integer id){
        return disposedGoodRespository.existsById(id);
    }

    public DisposedGood updateDisposedGoodById(Integer id, DisposedGood request){
        DisposedGood disposedGood = getDisposedGoodById(id);

        Optional.of(request.getDisposal_id()).ifPresent(disposedGood::setDisposal_id);
        Optional.ofNullable(request.getReason()).ifPresent(disposedGood::setReason);
        Optional.of(request.getQuantity()).ifPresent(disposedGood::setQuantity);
        Optional.of(request.getItem_id()).ifPresent(disposedGood::setItem_id);

        return disposedGoodRespository.save(disposedGood);
    }
}
