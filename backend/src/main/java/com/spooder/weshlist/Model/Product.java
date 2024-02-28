package com.spooder.weshlist.Model;

import java.util.*;

import org.hibernate.annotations.DynamicInsert;

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
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long product_id;

    private String image_name;
    
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "product", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductDetail> detail = new ArrayList<ProductDetail>();

    @Column(nullable = false)
    private String uploader;

    // Nullable Fields    
    private Date uploaded_date;

    private String updator;

    private Date updated_date;

    private int positive_point;

    private int negative_point;

    @ElementCollection
    @CollectionTable(name = "product_user_ratings", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "is_positive")
    private Map<String, Boolean> userRatings;
}