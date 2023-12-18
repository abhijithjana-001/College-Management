package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/department/file")
public class DepartmentFileController {
    @Value("${file.path}")
    private  String uploadDir;
    @Autowired
    private DepartmentFileService departmentFileService;
    @PostMapping("/upload")
    public ResponseEntity<Responsedto> handleFileUpload(@RequestParam("file") MultipartFile files[]) {
        Responsedto upload = departmentFileService.upload(files);
        return ResponseEntity.ok(upload);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<Responsedto> handleFileDelete(@PathVariable String filename) {
        Responsedto deleteFile = departmentFileService.deleteFile(filename);
        return ResponseEntity.ok(deleteFile);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) throws IOException {
        ImageData imageData=departmentFileService.findByName(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(imageData.contenttype()));
        headers.setContentDispositionFormData("attachment", filename);
        return ResponseEntity.status(200).headers(headers).contentType(MediaType.valueOf(imageData.contenttype())).body(imageData.image());
    }
}