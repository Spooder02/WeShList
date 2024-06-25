package com.spooder.weshlist.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spooder.weshlist.service.FileService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/file")
public class FileController {
    
    @Autowired
    private FileService fileService;

    public ResponseEntity<String> uploadImage(MultipartFile imageFile) {
        return fileService.uploadImage(imageFile);
    }
    
}
