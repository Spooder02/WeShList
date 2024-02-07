package com.spooder.weshlist.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spooder.weshlist.Model.Product;
import com.spooder.weshlist.service.ProductService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
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

