package com.spooder.weshlist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.spooder.weshlist.Model.User;
import com.spooder.weshlist.dto.IdDto;
import com.spooder.weshlist.dto.LoginDto;
import com.spooder.weshlist.dto.TelDto;
import com.spooder.weshlist.repository.UserRepository;
import com.spooder.weshlist.security.JwtProvider;
import com.spooder.weshlist.service.AuthService;
import com.spooder.weshlist.service.ProductService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

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

    @PostMapping("/findId")
    public ResponseEntity<String> getUserIdFromTel(@RequestBody TelDto tel) {
        try {
            return ResponseEntity.ok(authService.getUserId(tel.getTel()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestBody IdDto id) {
        try {
            logger.info(id.getId());
            User user = authService.getUser(id.getId());
            String userId = user.getID();
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody LoginDto user) {
        logger.info(user.getId()+" is id / " + user.getPassword() + " is password");
        try {
            authService.resetPassword(authService.getUser(user.getId()).getKey_id(), user.getPassword());
            return ResponseEntity.ok("Success on resetting password");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
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
            headers.add("Authorization", jwtProvider.createToken(request.getId(), authService.getUser(request.getId()).getNickname(), authService.getUser(request.getId()).getID()));
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
    
    @CrossOrigin(origins = "*", exposedHeaders = "Authorization")
    @PostMapping("/verifyUser")
    public ResponseEntity<Boolean> verifyUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (token != null) {
            return ResponseEntity.ok(jwtProvider.validateToken(token));
        }
        return ResponseEntity.notFound().build();
    }
}
