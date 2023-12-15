package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.entity.DepartmentImgEntity;
import com.example.CollegeManagment.repository.DepartmentImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class DepartmentImgService {
    @Autowired
    private final DepartmentImgRepository departmentImgRepository;

    @Autowired
    public DepartmentImgService(DepartmentImgRepository departmentImgRepository) {
        this.departmentImgRepository = departmentImgRepository;
    }

    // Create operation
    public DepartmentImgEntity createDepartmentImage(String name, String type, MultipartFile file) throws IOException {
        // Create a new DepartmentImgEntity
        DepartmentImgEntity departmentImgEntity = new DepartmentImgEntity();
        departmentImgEntity.setName(name);
        departmentImgEntity.setType(type);
        departmentImgEntity.setSize(file.getSize());
        departmentImgEntity.setFilePath(saveFile(file));

        // Set the creation timestamp
        departmentImgEntity.setCreated(LocalDateTime.now());

        // Save the entity to the database
        return departmentImgRepository.save(departmentImgEntity);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Define the upload directory (change this to your desired directory)
        String uploadDir = "C://Users//user432//Desktop//fileUpload";

        // Create the directory if it doesn't exist
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename to avoid overwriting existing files
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Save the file to the upload directory
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    // Read operations

    public Resource loadImageAsResource(Long id) throws FileNotFoundException {
        // Retrieve the file path from the database
        String filePath = departmentImgRepository.findById(id)
                .map(DepartmentImgEntity::getFilePath)
                .orElseThrow(() -> new FileNotFoundException("Image not found with id: " + id));

        try {
            // Load the file as a resource
            Path file = Path.of("C://Users//user432//Desktop//fileUpload").resolve(filePath);  // adjust the directory based on your setup
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Image not found with id: " + id);
            }
        } catch (IOException e) {
            throw new BadRequest("Error loading image with id: " + id);
        }
    }

    // Update operation


    // Delete operation
    @Transactional
    public void deleteImage(Long id) throws FileNotFoundException {
        // Retrieve the file path from the database
        String filePath = departmentImgRepository.findById(id)
                .map(DepartmentImgEntity::getFilePath)
                .orElseThrow(() -> new FileNotFoundException("Image not found with id: " + id));

        // Delete the entity from the database
        departmentImgRepository.deleteById(id);

        // Delete the associated image file
        deleteFile(filePath);
    }

    private void deleteFile(String filePath) {
        try {
            Path fileToDelete = Path.of("C://Users//user432//Desktop//fileUpload").resolve(filePath); // adjust the directory based on your setup
            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            throw new BadRequest("Error deleting image file: " + filePath);
        }
    }

}
