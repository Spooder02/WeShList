package com.spooder.weshlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spooder.weshlist.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
