package com.example.banking_app.service;

import com.example.banking_app.entity.User;
import com.example.banking_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    public User getUserDetailsByName(String name){
        User user = userRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

}
