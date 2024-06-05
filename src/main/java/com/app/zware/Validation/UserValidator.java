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

    Integer wId = user.getWarehouse_id(); //Warehouse ID
    if ( wId != null && !warehouseRespository.existsById(wId)
    ) {
      return "Warehouse id is not valid";
    }

    return "";
  }

  public String checkPut(Integer userId, User user) {
    if (!userRepository.existsById(userId)) {
      return ("User id is not valid");
    }
    return checkPost(user);
  }

  public String checkGet(Integer userId) {
    return (userRepository.existsById(userId) ? "" : "User id s not valid");
  }

  public String checkDelete(Integer userId) {
    return checkGet(userId);
  }

}
