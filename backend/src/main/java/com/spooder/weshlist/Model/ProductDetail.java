package com.spooder.weshlist.Model;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor // 파라미터가 없는 생성자를 만들어 줌.
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_detail_key;

    @Column(nullable = false)
    private String changed_point;

    @Column(nullable = false)
    private float before_detail;

    @Column(nullable = false)
    private float after_detail;

    @Column(nullable = false)
    private String unit;

    @Builder
    public ProductDetail(ProductDetail productDetail) {
        this.product_detail_key = productDetail.product_detail_key;
        this.changed_point = productDetail.changed_point;
        this.before_detail = productDetail.before_detail;
        this.after_detail = productDetail.after_detail;
        this.unit = productDetail.unit;
    }

    public Long getProduct_detail_key() { return this.product_detail_key; }
    public void setProduct_detail_key(Long product_detail_key) { this.product_detail_key = product_detail_key; }

    public String getChanged_point() { return this.changed_point; }
    public void setChanged_point(String changed_point) {this.changed_point = changed_point;}

    public float getBefore_detail() {return this.before_detail;}
    public void setBefore_detail(float before_detail) {this.before_detail = before_detail;}

    public float getAfter_detail() {return this.after_detail;}
    public void setAfter_detail(float after_detail) {this.after_detail = after_detail;}

    public String getUnit() {return this.unit;}
    public void setUnit(String unit) {this.unit = unit;}
    
}