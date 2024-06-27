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
    System.out.println(user.getPhone());

    //MUST-HAVE fields
    if (user.getEmail() == null){
      return "Email is required";
    }

    if (user.getPassword() == null){
      return "Password is required";
    }

    if (user.getName() == null){
      return "Name is required";
    }

    if (user.getRole() == null){
      return "Role is required";
    }

    if (user.getRole().equals("manager") && user.getWarehouse_id()== null){
      return "Warehouse Id is required";
    }

    //Check valid
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";
    if (!user.getEmail().matches(emailRegex)) {
      return "Email is not valid";
    }

    if ( user.getPassword().length() < 6) {
      return "Password is at least 6 characters";
    }

    if (!user.getRole().equals("admin") && !user.getRole().equals("manager")) {
      return "Role is not valid";
    }

    if (user.getPhone() != null && !user.getPhone().matches("^\\d+$")) {
      return "Phone is not valid";
    }

    Integer wId = user.getWarehouse_id(); //Warehouse ID
    if (wId != null && !warehouseRespository.existsById(wId)
    ) {
      return "Warehouse id is not valid";
    }

    //this condition need to be in the last
    if (userRepository.findByEmail(user.getEmail()) != null) {
      return "Email has been used";
    }

    //IF pass all
    return "";
  }

  public String checkPut(Integer userId, User mergedUser) {
    if (userId == null || !checkUserId(userId)) {
      return ("User id is not valid");
    }
    String checkPostMessage = checkPost(mergedUser);
    return (checkPostMessage.equals("Email has been used")) ? "" : checkPostMessage;
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

  public boolean checkUserId(Integer id) {
    return userRepository.existByIdAndIsDeletedFalse(id);
  }

  public boolean isValidPhoneNumber(String phoneNumber) {
    // Define a regex for phone number validation, exactly 10 digits
    String phoneRegex = "^\\d{10}$";

    Pattern pattern = Pattern.compile(phoneRegex);
    Matcher matcher = pattern.matcher(phoneNumber);

    return matcher.matches();
  }

}
