package com.spooder.weshlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import com.spooder.weshlist.Model.Product;
import com.spooder.weshlist.Model.ProductDetail;
import com.spooder.weshlist.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public Product addProduct(Product product, MultipartFile imageFile) {
        product.setUploaded_date(new Date());
        product.setUpdated_date(new Date());
        for (ProductDetail productDetail : product.getDetail()) {
            productDetail.setProduct(product);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String originalFilename = imageFile.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String imageName = product.getName().replaceAll("\\s+", "_") + fileExtension;
                saveImageFile(imageFile, imageName);
                product.setImage_name(imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (product.getUploader() == null) {
            product.setUploader("익명");
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product product) {
        product.setProduct_id(id);
        return productRepository.save(product);   
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private void saveImageFile(MultipartFile imageFile, String filename) throws IOException {
        Files.copy(imageFile.getInputStream(), Paths.get("backend/src/main/resources/static/image/", filename), StandardCopyOption.REPLACE_EXISTING);
    }
}
