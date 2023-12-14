package com.example.CollegeManagment.controller.filehandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/teacher/file")
public class TeacherFileHandling {

    private final String uploadDir = "C://Users//user0101//Desktop//javagroupproject//images//";

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file to the local directory
            for(MultipartFile files:file) {
                Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
                file.transferTo(filePath.toFile());
            }
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading the file.");
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir,fileName);
        byte[] imageData= Files.readAllBytes(filePath);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        File file = new File(uploadDir, filename);
        if (file.exists() && file.delete()) {
            System.out.println("hi---------------------");
            return ResponseEntity.ok("File deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("File not found or couldn't be deleted.");
        }
    }
}
