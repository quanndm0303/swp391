package com.app.zware.Service;

import com.app.zware.Entities.Item;
import com.app.zware.Repositories.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  @Autowired
  ItemRepository itemRepository;

  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  public Item createItem(Item request) {
    request.setIsdeleted(false);
    return itemRepository.save(request);
  }

  public Item getItemById(Integer id) {
    return itemRepository.findById(id).orElse(null);
  }

  public boolean checkIdItemExist(Integer id) {
    return itemRepository.existsByIdAndIsDeletedFalse(id);
  }

  public void deleteItemById(Integer id) {
    Item item = getItemById(id);
    item.setIsdeleted(true);
    itemRepository.save(item);
//    itemRepository.deleteById(id);
  }

  public Item merge(Integer id, Item request) {
    Item oldItem = getItemById(id);
    if (oldItem == null) {
      return null;
    }
    Optional.ofNullable(request.getProduct_id()).ifPresent(oldItem::setProduct_id);
    Optional.ofNullable(request.getExpire_date()).ifPresent(oldItem::setExpire_date);
    oldItem.setIsdeleted(false);
    return oldItem;

  }

  public Item update(Item item) {
    return itemRepository.save(item);
  }

}
