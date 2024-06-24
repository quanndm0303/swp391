package com.app.zware.Validation;

import com.app.zware.Entities.Item;
import com.app.zware.Entities.Product;
import com.app.zware.Entities.WarehouseItems;
import com.app.zware.Repositories.CategoryRepository;
import com.app.zware.Repositories.ItemRepository;
import com.app.zware.Repositories.ProductRepository;
import com.app.zware.Repositories.WarehouseItemsRepository;
import com.app.zware.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidator {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  WarehouseItemsRepository warehouseItemsRepository;

  @Autowired
  ItemService itemService;

  public String checkPost(Product product) {
    if (product.getName() == null || product.getName().isEmpty()) {
      return "Name is not valid !";
    }
    if (product.getSupplier() == null || product.getSupplier().isEmpty()) {
      return "Supplier is not valid";
    }
    if (product.getMeasure_unit() == null || product.getMeasure_unit().isEmpty()) {
      return "Measure unit is not valid";
    }
    if (product.getCategory_id() == null
        ||  !categoryRepository.existsByIdAndIsDeletedFalse(product.getCategory_id())) {
      return "Not found Category ID";
    }
    return "";
  }

  public String checkPut(Integer id, Product product) {
    if (id == null || !checkProductId(id)) {
      return "Not found product ID";
    }
//        if(product.getCategory_id() == null|| !productRepository.existsByIdAndIsDeletedFalse(product.getCategory_id())) {
//            return "Not found Category ID";
//        }
    return checkPost(product);
  }

  public String checkGet(Integer id) {
    if (!checkProductId(id)) {
      return "Not found ID product";
    }
    return "";
  }

  public String checkDelete(Integer id) {
    if (!checkProductId(id)) {
      return "Not found ID product";
    }
    List<Item> listItemByProductId = itemRepository.findByProductId(id);
    //Find any items containing productId
    if(!listItemByProductId.isEmpty()){
      //Find out if the item is still in quantity
      for(Item item : listItemByProductId){
        List<WarehouseItems> warehouseItems = warehouseItemsRepository.findByItemId(item.getId());
         if(!warehouseItems.isEmpty())
          return "Cannot delete this product, there are some warehouse in this product that are in quantity";
      }
    }
    // if delete product success, item contain productId will deleted
    itemService.deletedItemByProductId(id);
    return "";
  }

  public boolean checkProductId(Integer id) {
    return productRepository.existsByIdAndIsDeletedFalse(id);
  }


}
