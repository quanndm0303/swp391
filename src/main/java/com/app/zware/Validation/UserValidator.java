package com.app.zware.Validation;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import com.app.zware.Repositories.WarehouseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

  @Autowired
  UserRepository userRepository;

  @Autowired
  WarehouseRespository warehouseRespository;

  /*
   *   return '' (empty) if OK
   *   return non-empty if NOT OK
   * */

  public String checkPost(User user) {
    if (user.getName().isBlank()) {
      return "Name is not valid";
    }

    if (!user.getEmail().matches("^(.+)@(\\S+)$")) {
      return "Email is not valid";
    }

    if (userRepository.findByEmail(user.getEmail()) != null) {
      return "Email has been used";
    }

    if (user.getPassword().length() < 6) {
      return "Password is not valid";
    }

    if (!user.getRole().equals("admin") && !user.getRole().equals("manager")) {
      return "Role is not valid";
    }

    if (
        user.getWarehouse_id() != null &&
        !checkWarehouseId(user.getWarehouse_id())
    ) {
      return "Warehouse id is not valid";
    }

    return "";
  }

  public String checkPut(User user) {
    return checkPost(user);
  }

  public String checkGet(Integer userId) {
    if (!checkUserId(userId)) {
      return "User id is not valid";
    } else {
      return "";
    }
  }

  public String checkDelete(Integer userId){
    return checkGet(userId);
  }

  //#####
  private boolean checkUserId(Integer userId) {
    return userRepository.findById(userId).orElse(null) != null;
  }

  private boolean checkWarehouseId(Integer warehouseId) {
    return warehouseRespository.findById(warehouseId).orElse(null) != null;
  }

}
