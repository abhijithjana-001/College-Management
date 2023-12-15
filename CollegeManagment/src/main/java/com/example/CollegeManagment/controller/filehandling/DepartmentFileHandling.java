package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public  ResponseEntity<Responsedto> handleFileDelete(@PathVariable String filename) {
        File file=new File(uploadDir,filename);
        if(file.exists() && file.delete()){

            return ResponseEntity.ok(new Responsedto<>(true,"File delete successfully!",null));
        }
        else{
            return  ResponseEntity.status(404).body(new Responsedto<>(false,"File delete successfully!",null));
        }
    }

    @GetMapping("/list")
    public void getallFile( HttpServletResponse response) throws IOException {
        List<Path> paths = new ArrayList<>();
        Path dir = Paths.get(uploadDir);
        Files.walk(dir)
                .filter(file -> Files.isRegularFile(file))
                .forEach(path -> paths.add(path));

        response.setContentType("application/zip"); // zip archive format
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("download.zip", StandardCharsets.UTF_8)
                .build()
                .toString());


        // Archiving multiple files and responding to the client
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for (Path file : paths) {
                try (InputStream inputStream = Files.newInputStream(file)) {
                    zipOutputStream.putNextEntry(new ZipEntry(file.getFileName().toString()));
                    StreamUtils.copy(inputStream, zipOutputStream);
                    zipOutputStream.flush();
                }
            }

        }
    }
}
