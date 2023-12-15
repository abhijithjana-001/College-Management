package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.DepartmentImgEntity;
import com.example.CollegeManagment.service.impl.DepartmentImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;


@RestController
@RequestMapping("/api/department-img")
public class DepartmentImgController {

    private final DepartmentImgService departmentImgService;

    @Autowired
    public DepartmentImgController(DepartmentImgService departmentImgService) {
        this.departmentImgService = departmentImgService;
    }

    // Create operation
    @PostMapping("/upload")
    public ResponseEntity<DepartmentImgEntity> uploadImage(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file) {
        try {
            DepartmentImgEntity createdDepartmentImg = departmentImgService.createDepartmentImage(name, type, file);
            return new ResponseEntity<>(createdDepartmentImg, HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle file processing errors, e.g., logging or returning an error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read operations

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws FileNotFoundException {
        // Retrieve the file path from the service
        Resource resource = departmentImgService.loadImageAsResource(id);

        // Set the content type and attachment header for the response
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // adjust the content type based on your file type
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //Delete operation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        try {
            // Call the service method to delete the image
            departmentImgService.deleteImage(id);
            String successMessage = "Image with ID " + id + " deleted successfully.";
            return new ResponseEntity<>(successMessage, HttpStatus.NO_CONTENT);
        } catch (ItemNotFound | FileNotFoundException e) {
            // Handle the case where the image or entity is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequest e) {
            // Handle other internal server errors
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
