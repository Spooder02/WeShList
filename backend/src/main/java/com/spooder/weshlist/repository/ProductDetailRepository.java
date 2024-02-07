package com.spooder.weshlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spooder.weshlist.Model.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    
}
