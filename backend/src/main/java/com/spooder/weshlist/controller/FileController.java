package com.spooder.weshlist.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.activation.MimetypesFileTypeMap;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/static")
public class FileController {
    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            File file = ResourceUtils.getFile("file:backend/image/"+filename);
            if (file.exists()) {
                byte[] imageFile = Files.readAllBytes(file.toPath());
                String mimeType = new MimetypesFileTypeMap().getContentType(file);
                MediaType mediaType = MediaType.parseMediaType(mimeType);

                return ResponseEntity.ok()
                                    .contentType(mediaType)
                                    .body(imageFile);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }
    
}
