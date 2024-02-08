package com.spooder.weshlist.Model;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor // 파라미터가 없는 생성자를 만들어 줌.
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_detail_key;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="product_no")
    private Product product;

    @Column(nullable = false)
    private String changed_point;

    @Column(nullable = false)
    private float before_detail;

    @Column(nullable = false)
    private float after_detail;

    @Column(nullable = false)
    private String unit;
}