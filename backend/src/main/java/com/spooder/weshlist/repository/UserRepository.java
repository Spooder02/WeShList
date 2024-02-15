package com.spooder.weshlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spooder.weshlist.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByID(String iD);
}
