package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.TeacherFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/teacher/file")
public class TeacherFileHandlingController {

    @Value("${file.path}")
    private String uploadDir;

    @Autowired
    private TeacherFileService teacherFileService;

    @PostMapping("/upload")
    public ResponseEntity<Responsedto> handleFileUpload(@RequestParam("file") MultipartFile file) {
        Responsedto upload=teacherFileService.upload(file);
        return ResponseEntity.ok(upload);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        ImageData imageData=teacherFileService.findByName(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(imageData.contenttype()));
        headers.setContentDispositionFormData("attachment", fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).contentType(MediaType.valueOf(imageData.contenttype()))
                .body(imageData.image());
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<Responsedto> deleteFile(@PathVariable String filename) {
       Responsedto deleteFile=teacherFileService.deleteFile(filename);
       return ResponseEntity.ok(deleteFile);
    }
}
