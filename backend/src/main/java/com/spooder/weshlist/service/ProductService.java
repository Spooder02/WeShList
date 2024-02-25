package com.spooder.weshlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.spooder.weshlist.Model.Product;
import com.spooder.weshlist.Model.ProductDetail;
import com.spooder.weshlist.repository.ProductDetailRepository;
import com.spooder.weshlist.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    private final String imageDirectory = "backend/image/";
    
    public Product addProduct(Product product, MultipartFile imageFile) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date now = calendar.getTime();

        product.setUploaded_date(now);
        product.setUpdated_date(now);
        for (ProductDetail productDetail : product.getDetail()) {
            if (productDetail.isUnknown())
                productDetail.setDetailsNull();
            else productDetail.setUnknown(false);
            productDetail.setProduct(product);
        }
        replaceImageFile(product, imageFile);
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

    public ProductDetail getProductDetailById(Long detailId) {
        return productDetailRepository.getById(detailId);
    }

    public ProductDetail getProductDetailByChangedPoint(String changed_point, Product product) {
        return productDetailRepository.findByChangedPointAndProduct(changed_point, product);
    }

    public void deleteProduct(Long id) throws IOException {
        Product product = getProductById(id);
        Path filePath = Paths.get(imageDirectory + product.getImage_name());
        Files.delete(filePath);
        productRepository.deleteById(id);
    }

    private void saveImageFile(MultipartFile imageFile, String filename) throws IOException {
        Files.copy(imageFile.getInputStream(), Paths.get(imageDirectory, filename), StandardCopyOption.REPLACE_EXISTING);
    }

    public void replaceImageFile(Product product, MultipartFile imageFile) {
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
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

}
