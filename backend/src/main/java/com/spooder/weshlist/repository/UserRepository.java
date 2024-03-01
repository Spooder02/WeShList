package com.spooder.weshlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spooder.weshlist.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByID(String iD);
    
    User findByTel(String tel);
}
