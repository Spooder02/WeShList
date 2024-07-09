package com.spooder.weshlist.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ResponseEntity<String> uploadImage(MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) { // 파일이 있을 경우에만 시도
            try {
                String filename = imageFile.getOriginalFilename();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(imageFile.getContentType());
                metadata.setContentLength(imageFile.getSize());
                amazonS3Client.putObject(bucket, filename, imageFile.getInputStream(), metadata);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok("image uploaded");
    }

    public ResponseEntity<String> deleteImage(String fileName) {
        try {
            boolean isFileExist = amazonS3Client.doesObjectExist(bucket, fileName);
            if (isFileExist) {
                amazonS3Client.deleteObject(bucket, fileName);
                logger.info("Excuted!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Image Deleted");
    }
}
