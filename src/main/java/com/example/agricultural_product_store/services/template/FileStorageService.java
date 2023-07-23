package com.example.agricultural_product_store.services.template;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

public interface FileStorageService {
    public String save(MultipartFile file) throws IOException;
}
