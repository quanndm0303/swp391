package com.app.zware.Service;

import com.app.zware.Entities.GoodsDisposal;
import com.app.zware.Repositories.GoodsDisposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsDisposalService {

    @Autowired
    GoodsDisposalRepository goodsDisposalRepository;

    public List<GoodsDisposal> findAllGoods(){
        return goodsDisposalRepository.findAll();
    }

    public GoodsDisposal getGoodsById(Integer id){
        return goodsDisposalRepository.findById(id).orElse(null);
    }

    public GoodsDisposal createGoodsDisposed(GoodsDisposal request){
        return goodsDisposalRepository.save(request);
    }

    public void deleteById(Integer id){
        goodsDisposalRepository.deleteById(id);
    }

    public Boolean checkIdExist(Integer id){
       return goodsDisposalRepository.existsById(id);
    }

    public GoodsDisposal update(Integer id, GoodsDisposal request){
        GoodsDisposal goodsDisposal = getGoodsById(id);

        Optional.ofNullable(request.getDate()).ifPresent(goodsDisposal::setDate);
        Optional.ofNullable(request.getStatus()).ifPresent(goodsDisposal::setStatus);
        Optional.ofNullable(request.getWarehouse_id()).ifPresent(goodsDisposal::setWarehouse_id);

        return goodsDisposalRepository.save(goodsDisposal);
    }
}
