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

  public User getById(Integer id) {
    return userRepository.findById(id).orElse(null);
  }

  public User getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void deleteUserById(Integer id) {
    userRepository.deleteById(id);
  }

  public boolean checkIdUserExist(Integer id) {
    return userRepository.existsById(id);
  }

  public User update(Integer id, User mergedUser) {
    return userRepository.save(mergedUser);
  }

  public User merge(Integer oldUserId, User newUser) {
    User oldUser = userRepository.findById(oldUserId).orElse(null);
    if (oldUser == null) {
      return null;
    }

    //Update non-null field of newUser -> oldUser
    Optional.ofNullable(newUser.getName()).ifPresent(oldUser::setName);
    Optional.ofNullable(newUser.getAvatar()).ifPresent(oldUser::setAvatar);
    Optional.ofNullable(newUser.getDate_of_birth()).ifPresent(oldUser::setDate_of_birth);
    Optional.ofNullable(newUser.getPhone()).ifPresent(oldUser::setPhone);
    Optional.ofNullable(newUser.getGender()).ifPresent(oldUser::setGender);
    Optional.ofNullable(newUser.getEmail()).ifPresent(oldUser::setEmail);
    Optional.ofNullable(newUser.getRole()).ifPresent(oldUser::setRole);
    Optional.ofNullable(newUser.getPassword()).ifPresent(oldUser::setPassword);

    return oldUser;   //Updated
  }

  // Get request maker from JWT
  public User getRequestMaker(HttpServletRequest request) {
    String jwt = JwtUtil.getJwtToken(request);
    String email = JwtUtil.extractEmail(jwt);
    return userRepository.findByEmail(email);
  }
}
