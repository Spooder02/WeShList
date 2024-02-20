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

    @Column(nullable = false, unique = true)
    private String changed_point;

    @Column
    private Float before_value;

    @Column
    private Float after_value;

    @Column
    private String unit;

    @Column(nullable = false)
    private boolean unknown;

    public void setDetailsNull() {
        this.before_value = null;
        this.after_value = null;
        this.unit = null;
    }
}