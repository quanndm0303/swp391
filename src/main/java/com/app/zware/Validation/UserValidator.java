package com.app.zware.Validation;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import com.app.zware.Repositories.WarehouseRespository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    if (!isValidPhoneNumber(user.getPhone())) {
      return "Phone is not valid";

    }

    if (user.getPassword().length() < 6) {
      return "Password is not valid";
    }

    if (!user.getRole().equals("admin") && !user.getRole().equals("manager")) {
      return "Role is not valid";
    }

    Integer wId = user.getWarehouse_id(); //Warehouse ID
    if (wId != null && !warehouseRespository.existsById(wId)
    ) {
      return "Warehouse id is not valid";
    }

    //If return this to checkPut, it is OK.
    if (userRepository.findByEmail(user.getEmail()) != null) {
      return "Email has been used";
    }

    return "";
  }


  //fix trung email nguoi khac
  public String checkPut(Integer userId, User mergedUser) {
    if (userId == null || !checkUserId(userId)) {
      return ("User id is not valid");
    }
    String checkPostMessage = checkPost(mergedUser);
    return (checkPostMessage.equals("Email has been used")) ? "" : checkPostMessage;
  }

  public boolean checkUserId(Integer id) {
    return userRepository.existByIdAndIsDeletedFalse(id);
  }

  public String checkGet(Integer userId) {
    if (!checkUserId(userId)) {
      return "Not found User Id";
    }
    return "";
  }

  public String checkDelete(Integer userId) {
    return checkGet(userId);
  }

  public boolean isValidPhoneNumber(String phoneNumber) {
    // Define a regex for phone number validation,exactly 10 digits

    String phoneRegex = "^\\d{10}$";

    Pattern pattern = Pattern.compile(phoneRegex);
    Matcher matcher = pattern.matcher(phoneNumber);

    return matcher.matches();
  }

}
