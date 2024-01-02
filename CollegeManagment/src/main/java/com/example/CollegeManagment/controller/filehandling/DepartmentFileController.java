package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/department/file")
public class DepartmentFileController {
    @Value("${file.path}")
    private  String uploadDir;
    @Setter
    @Autowired
    private DepartmentFileService departmentFileService;

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) throws IOException {
        ImageData imageData=departmentFileService.findByName(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(imageData.contentType()));
        headers.setContentDispositionFormData("attachment", filename);
        return ResponseEntity.status(200).headers(headers).contentType(MediaType.valueOf(imageData.contentType())).body(imageData.image());
    }

}