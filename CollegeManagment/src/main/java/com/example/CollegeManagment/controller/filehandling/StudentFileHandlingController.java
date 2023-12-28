package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.service.impl.StudentFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/student/file")
public class StudentFileHandlingController {
  @Value("${file.path}")
  private  String uploadDir;
@Autowired
private StudentFileServiceImpl studentFileServiceImpl;
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getfile(@PathVariable String filename) throws IOException {
        ImageData imageData= studentFileServiceImpl.findByName(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(imageData.contenttype()));
        headers.setContentDispositionFormData("attachment", filename);
        return ResponseEntity.status(200).headers(headers).contentType(MediaType.valueOf(imageData.contenttype())).body(imageData.image());
    }



}