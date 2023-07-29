package com.example.agricultural_product_store.services;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.agricultural_product_store.services.template.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;
    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "litchitech",
            "api_key", "547396346579771",
            "api_secret", "EZz63do19FEKUREbduBIZWTHGWE",
            "secure", true));

    public FileStorageServiceImpl() {
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

    public String upload(MultipartFile file) throws IOException {
        try {
            Map response = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder" , "agri_shop",
                            "resource_type", "auto"));
            return response.get("secure_url").toString();
        }
        catch (IOException e) {
            throw new IOException("Could not store file " + file.getOriginalFilename() + ". Please try again!");
        }
    }
}
