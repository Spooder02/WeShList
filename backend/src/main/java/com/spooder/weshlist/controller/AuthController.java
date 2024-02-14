package com.spooder.weshlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.spooder.weshlist.Model.User;
import com.spooder.weshlist.service.AuthService;

import org.springframework.web.bind.annotation.*;




@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping
    @ResponseBody
    public String showMain() {
        return "Authentication Mainpage";
    }
    
    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User savedUser = authService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        authService.deleteById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
}
