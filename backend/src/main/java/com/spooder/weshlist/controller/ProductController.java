package com.spooder.weshlist.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spooder.weshlist.Model.Product;
import com.spooder.weshlist.service.ProductService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product, @RequestPart(name = "imageFile", required = false) MultipartFile imageFile) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product savedProduct = productService.addProduct(product, imageFile);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
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
    public Product updateProduct(@PathVariable Long product_id, @RequestBody Product product) {
        return productService.updateProduct(product_id, product);
    }

    @DeleteMapping("/{product_id}")
    public void deleteProduct(@PathVariable Long product_id) {
        productService.deleteProduct(product_id);
    }
}

