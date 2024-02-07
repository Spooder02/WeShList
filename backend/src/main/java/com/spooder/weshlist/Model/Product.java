package com.spooder.weshlist.Model;

import com.spooder.weshlist.repository.ProductRepository;

import java.util.*;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor // 파라미터가 없는 생성자를 만들어 줌.
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long product_id;
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="detail")
    private List<ProductDetail> detail;

    @Column(nullable = false)
    private String uploader;

    @Column
    private Date uploaded_date;
    
    @Column
    private String updator;

    @Column
    private Date updated_date;

    @Column
    private int positive_point;

    @Column
    private int negative_point;

    @Builder
    public Product(Long product_id, String name, int price, String brand, String category, String uploader, Date uploaded_date, String updator, Date updated_date, int positive_point, int negative_point) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.uploader = uploader;
        this.uploaded_date = uploaded_date;
        this.updator = updator;
        this.updated_date = updated_date;
        this.positive_point = positive_point;
        this.negative_point = negative_point;
    }

    public Product(Product product) {
        this.product_id = product.product_id;
        this.name = product.name;
        this.price = product.price;
        this.brand = product.brand;
        this.category = product.category;
        this.uploader = product.uploader;
        this.uploaded_date = product.uploaded_date;
        this.updator = product.updator;
        this.updated_date = product.updated_date;
        this.positive_point = product.positive_point;
        this.negative_point = product.negative_point;
    }

    public Long getId() { return this.product_id; }
    public void setId(Long id) { this.product_id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return this.price; }
    public void setPrice(int price) { this.price = price; }

    public String getBrand() { return this.brand; }
    public void setBrand(String brand) { this.brand = brand;}

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public List<ProductDetail> getDetails() { return this.detail; }
    public void setDetails(List<ProductDetail> productDetail) { this.detail = productDetail; }

    public String getUploader() { return this.uploader; }
    public void setUploader(String uploader) { this.uploader = uploader; }

    public Date getUploaded_date() { return this.uploaded_date; }
    public void setUploaded_date(Date uploaded_date) { this.uploaded_date = uploaded_date; }
    
    public String getUpdator() { return this.updator; }
    public void setUpdator(String updator) { this.updator = updator; }

    public Date getUpdated_date() { return this.updated_date; }
    public void setUpdated_date(Date updated_date) { this.updated_date = updated_date; }

    public int getPositive_point() { return this.positive_point; }
    public void setPositive_point(int positive_point) { this.positive_point = positive_point; }

    public int getNegative_point() { return this.negative_point; }
    public void setNegative_point(int negative_point) { this.negative_point = negative_point; }
}