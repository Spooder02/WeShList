package com.spooder.weshlist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spooder.weshlist.Model.User;
import com.spooder.weshlist.dto.LoginDto;
import com.spooder.weshlist.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public User addUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActivated(true);
        user.setRole("user");
        return userRepository.save(user);
    }

    public boolean Login(LoginDto userdata) {
        User user = userRepository.findByID(userdata.getId());
        if (user != null && user.isActivated()) {
            return (passwordEncoder.matches(userdata.getPassword(), user.getPassword())? true : false);
        } else {
            return false;
        }
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUser(String id) {
        return userRepository.findByID(id);
    }

    public String getUserId(String tel) {
        User user = userRepository.findByTel(tel);
        return user.getID();
    }

    public User resetPassword(Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
