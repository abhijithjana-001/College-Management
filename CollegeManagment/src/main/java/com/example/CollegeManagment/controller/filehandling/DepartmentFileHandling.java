package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/department/file")
public class DepartmentFileHandling {
    private final String uploadDir = "C://Users//user432//Desktop//fileUpload//";

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {

            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
            file.transferTo(filePath.toFile());
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading the file.");
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> getfile(@PathVariable String filename) throws IOException {
        Path path=Paths.get(uploadDir,filename);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image= Files.readAllBytes(path);
        return ResponseEntity.status(200).contentType(MediaType.valueOf(contentType)).body(image);
    }

    @DeleteMapping("/delete/{filename}")
    public  ResponseEntity<Responsedto> handleFileDelete(@PathVariable String filename)
    {
        File file=new File(uploadDir,filename);
        if(file.exists() && file.delete()){

            return ResponseEntity.ok(new Responsedto<>(true,"File delete successfully!",null));
        }
        else{
            return  ResponseEntity.status(404).body(new Responsedto<>(false,"File delete successfully!",null));
        }
    }
}
