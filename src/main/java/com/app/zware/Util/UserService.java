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

    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found User"));
    }

    public void deleteUserById(int id){
         userRepository.deleteById(id);
    }

    public User updateUserById(int id, User userRequest){
        User user = getUserById(id);

        if(userRequest.getName()!=null){
            user.setName(userRequest.getName());
        }
        if(userRequest.getAvatar()!=null){
            user.setAvatar(userRequest.getAvatar());
        }
        if(userRequest.getDateofbirth()!=null){
            user.setDateofbirth(userRequest.getDateofbirth());
        }
        if(userRequest.getPhone()!=null){
            user.setPhone(userRequest.getPhone());
        }
        if(userRequest.getGender()!=null){
            user.setName(userRequest.getGender());
        }
        if(userRequest.getEmail()!=null){
            user.setEmail(userRequest.getEmail());
        }
        if(userRequest.getRole()!=null){
            user.setRole(userRequest.getRole());
        }
        if(userRequest.getPassword()!=null){
            user.setPassword(userRequest.getPassword());
        }
        if(userRequest.getWarehouse()!=null){
            user.setWarehouse(userRequest.getWarehouse());
        }

        return userRepository.save(user);
    }
}
