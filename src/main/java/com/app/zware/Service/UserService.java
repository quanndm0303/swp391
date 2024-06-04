package com.app.zware.Service;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import com.app.zware.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getById(int id) {
    return userRepository.findById(id).orElse(null);
  }

  public User getByEmail(String email){
    return userRepository.findByEmail(email);
  }

  public void deleteUserById(int id) {
    userRepository.deleteById(id);
  }

  public boolean checkIdUserExist(int id) {
    return userRepository.existsById(id);
  }

  public User update(int id, User userRequest) {
    User user = getById(id);

    Optional.ofNullable(userRequest.getName()).ifPresent(user::setName);
    Optional.ofNullable(userRequest.getAvatar()).ifPresent(user::setAvatar);
    Optional.ofNullable(userRequest.getDate_of_birth()).ifPresent(user::setDate_of_birth);
    Optional.ofNullable(userRequest.getPhone()).ifPresent(user::setPhone);
    Optional.ofNullable(userRequest.getGender()).ifPresent(user::setGender);
    Optional.ofNullable(userRequest.getEmail()).ifPresent(user::setEmail);
    Optional.ofNullable(userRequest.getRole()).ifPresent(user::setRole);
    Optional.ofNullable(userRequest.getPassword()).ifPresent(user::setPassword);

    return userRepository.save(user);
  }

  // Get request maker from JWT
  public User getRequestMaker(HttpServletRequest request){
    String jwt = JwtUtil.getJwtToken(request);
    String email = JwtUtil.extractEmail(jwt);
    return userRepository.findByEmail(email);
  }
}
