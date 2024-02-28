package com.spooder.weshlist.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spooder.weshlist.Model.Product;
import com.spooder.weshlist.Model.ProductDetail;
import com.spooder.weshlist.service.ProductService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product, @RequestPart(name = "imageFile", required = false) MultipartFile imageFile) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(product.getUploader() + " is uploader");
        Product savedProduct = productService.addProduct(product, imageFile);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/{productId}/ratings")
    public ResponseEntity<String> addRating(@PathVariable Long productId, 
                                            @RequestParam String userId, 
                                            @RequestParam boolean isPositive) {
        productService.addRating(productId, userId, isPositive);
        return ResponseEntity.ok("평가가 성공적으로 추가/수정되었습니다.");
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{product_id}")
    public Product getProductById(@PathVariable Long product_id) {
        return productService.getProductById(product_id);
    }
    
    @PutMapping("/{product_id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long product_id, @ModelAttribute Product updatedProduct, @RequestPart(name = "imageFile", required = false) MultipartFile imageFile) {
        Product product = productService.getProductById(product_id);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date now = calendar.getTime();
        System.out.println(now);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        if (updatedProduct.getUploader() == null)
            product.setUploader("익명");
        else
            product.setUploader(updatedProduct.getUploader());

        if (updatedProduct.getImage_name() == null) productService.replaceImageFile(product, imageFile);
        System.out.println(updatedProduct.getBrand());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setBrand(updatedProduct.getBrand());
        product.setCategory(updatedProduct.getCategory());
        
        product.setUploaded_date(product.getUploaded_date());
        product.setUpdator(updatedProduct.getUpdator());
        product.setUpdated_date(now);

        List<ProductDetail> updatedDetails = updatedProduct.getDetail();
        for (ProductDetail updatedDetail : updatedDetails) {
            ProductDetail existingDetail = productService.getProductDetailByChangedPoint(updatedDetail.getChanged_point(), product);
            if (existingDetail == null) { // 존재하지 않는 속성이면 추가
                updatedDetail.setProduct(product);
                product.getDetail().add(updatedDetail);
            } else { // 아니라면 값 수정
                existingDetail.setBefore_value(updatedDetail.getBefore_value());
                existingDetail.setAfter_value(updatedDetail.getAfter_value());
                existingDetail.setUnit(updatedDetail.getUnit());
                existingDetail.setUnknown(updatedDetail.isUnknown());
            }
        }

        productService.updateProduct(product);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long product_id) {
        try {
            productService.deleteProduct(product_id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Error on deleting image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

