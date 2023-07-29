package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.services.FileStorageServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final FileStorageServiceImpl fileStorageService;

    public ImageController(FileStorageServiceImpl fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseData<List<String>> uploadImage(@RequestParam("files") MultipartFile[] files) {
        List<String> result = new ArrayList<>();
        Arrays.stream(files).map(file -> {
            try {
                result.add(fileStorageService.upload(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file;
        }).forEach(file -> System.out.println(file.getOriginalFilename()));
        return ResponseData.onSuccess(result);
    }
}
