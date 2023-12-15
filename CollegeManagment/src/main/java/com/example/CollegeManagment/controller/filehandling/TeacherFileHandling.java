package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.service.impl.TeacherFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@RestController
@RequestMapping("/teacher/file")
public class TeacherFileHandling {

    @Value("${file.path}")
    private String uploadDir;

    @Autowired
    private TeacherFileService teacherFileService;

    @PostMapping("/upload")
    public ResponseEntity<Responsedto> handleFileUpload(@RequestParam("file") MultipartFile file[]) {
        try {
            // Save the file to the local directory
            for(MultipartFile files:file) {
                Path filePath = Paths.get(uploadDir, files.getOriginalFilename());
                files.transferTo(filePath.toFile());
            }
            return ResponseEntity.ok(new Responsedto<>(true,file.length
                    +"File uploaded successfully!",null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Responsedto<>(false,
                    "Error uploading the file.",null));
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir,fileName);
        String contentType=Files.probeContentType(filePath);
        if(contentType==null){
            contentType=MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] imageData= Files.readAllBytes(filePath);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(contentType))
                .body(imageData);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        File file = new File(uploadDir, filename);
        if (file.exists() && file.delete()) {
            return ResponseEntity.ok("File deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("File not found or couldn't be deleted.");
        }
    }
}
