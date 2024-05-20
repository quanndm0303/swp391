package com.app.zware.Util;

import com.app.zware.Entities.User;
import com.app.zware.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUser(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found User"));
    }

    public void deleteUserById(int id){
         userRepository.deleteById(id);
    }

    public User updateUserById(int id, User userRequest){
        User user = getUserById(id);
        user.setName(userRequest.getName());
        user.setAvatar(userRequest.getAvatar());
        user.setDateofbirth(userRequest.getDateofbirth());
        user.setPhone(userRequest.getPhone());
        user.setGender(userRequest.getGender());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setPassword(userRequest.getPassword());

        return userRepository.save(user);
    }
}
