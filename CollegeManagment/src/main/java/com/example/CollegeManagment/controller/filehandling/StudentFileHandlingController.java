package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.service.impl.StudentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/student/file")
public class StudentFileHandlingController {
  @Value("${file.path}")
  private  String uploadDir;
@Autowired
private StudentFileService studentFileService;
    @PostMapping("/upload")
    public ResponseEntity<Responsedto> handleFileUpload(@RequestParam("file") MultipartFile files[]) {
        Responsedto upload = studentFileService.upload(files);
        return ResponseEntity.ok(upload);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<Responsedto> handleFileDelete(@PathVariable String filename) {
        Responsedto deletefile = studentFileService.deletefile(filename);
        return ResponseEntity.ok(deletefile);

    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getfile(@PathVariable String filename) throws IOException {
        Path path = studentFileService.findByName(filename);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image = Files.readAllBytes(path);
        return ResponseEntity.status(200).contentType(MediaType.valueOf(contentType)).body(image);
    }

//    @GetMapping()
//    public void getallFile( HttpServletResponse response) throws IOException {
//        List<Path> paths = new ArrayList<>();
//        Path dir = Paths.get(uploadDir);
//        Files.walk(dir)
//                .filter(file -> Files.isRegularFile(file))
//                .forEach(path -> paths.add(path));
//
//        response.setContentType("application/zip"); // zip archive format
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
//                .filename("download.zip", StandardCharsets.UTF_8)
//                .build()
//                .toString());
//
//
//        // Archiving multiple files and responding to the client
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
//            for (Path file : paths) {
//                try (InputStream inputStream = Files.newInputStream(file)) {
//                    zipOutputStream.putNextEntry(new ZipEntry(file.getFileName().toString()));
//                    StreamUtils.copy(inputStream, zipOutputStream);
//                    zipOutputStream.flush();
//                }
//            }
//
//        }
//    }

}