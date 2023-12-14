package com.example.CollegeManagment.controller.filehandling;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class DepartmentFileDownloader {

    private static final String FILE_DIRECTORY = "C://Users//user432//Desktop//fileUpload//";

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        // Load file as Resource
        Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
        Resource resource = new org.springframework.core.io.FileUrlResource(String.valueOf(filePath.toUri()));

        // Check if the file exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set Content-Disposition header for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        // Set the Content-Type header based on the file type
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Return the ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
