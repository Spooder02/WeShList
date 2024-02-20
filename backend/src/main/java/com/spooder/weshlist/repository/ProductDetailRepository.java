package com.spooder.weshlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spooder.weshlist.Model.Product;
import com.spooder.weshlist.Model.ProductDetail;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    ProductDetail getById(Long Id);
    @Query("SELECT pd FROM ProductDetail pd WHERE pd.changed_point = :changedPoint AND pd.product = :product")
    ProductDetail findByChangedPointAndProduct(String changedPoint, Product product);
}
