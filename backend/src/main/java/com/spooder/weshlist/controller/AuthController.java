package com.spooder.weshlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.spooder.dto.LoginDto;
import com.spooder.weshlist.Model.User;
import com.spooder.weshlist.security.JwtProvider;
import com.spooder.weshlist.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtProvider jwtProvider;

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

    @CrossOrigin(origins = "*", exposedHeaders = "Authorization")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto request) {
        if (authService.Login(request)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtProvider.createToken(request.getId(), authService.getUser(request.getId()).getNickname()));
            return new ResponseEntity<>("Successfully Logged In", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to login", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*", exposedHeaders = "Authorization")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (token != null) {
            if (jwtProvider.validateToken(token)) return new ResponseEntity<>("Successfully Logged out", HttpStatus.OK);
            return new ResponseEntity<>("Failed to logout", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Failed to logout", HttpStatus.BAD_REQUEST);
        }
    }
    
}
