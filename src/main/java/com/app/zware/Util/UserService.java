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
}
