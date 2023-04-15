package com.example.agricultural_product_store.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads");
    }

    public String save(MultipartFile file) throws IOException {
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            Files.copy(file.getInputStream(),this.fileStorageLocation.resolve(fileName));
            return "uploads/" + fileName;
        }
        catch (IOException e) {
            throw new IOException("Could not store file " + file.getOriginalFilename() + ". Please try again!");
        }
    }
}
