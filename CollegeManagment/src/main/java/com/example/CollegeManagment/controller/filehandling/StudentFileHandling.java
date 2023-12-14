package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student/file")
public class StudentFileHandling {
    private final String uploadDir = "C://Users//user453//Desktop//college2//files//";


    @PostMapping("/upload")
    public ResponseEntity<Responsedto> handleFileUpload(@RequestParam("file") MultipartFile files[]) {
        try {

            for (MultipartFile file : files) {
                Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
                file.transferTo(filePath.toFile());
            }

            return ResponseEntity.ok(new Responsedto<>(true, files.length+" File uploaded successfully!",null));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Responsedto<>(false,"File uploaded failed!",null));
        }
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

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getfile(@PathVariable String filename) throws IOException {
       Path path=Paths.get(uploadDir,filename);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image= Files.readAllBytes(path);
        return ResponseEntity.status(200).contentType(MediaType.valueOf(contentType)).body(image);
    }

//    @GetMapping()
//    public  ResponseEntity<List<Resource>> getallFile(){
//        List<Resource>  fileList=new ArrayList<>();
//        try {
//            Files.walk(Paths.get(uploadDir))
//                    .filter(file->Files.isRegularFile(file))
//                    .forEach(
//                           path ->{
//                               try {
////                                   Resource image=Files.readAllBytes(path);
////                                   Resource resource=new
////                                   fileList.add(resource);
//                               } catch (IOException e) {
//                                   throw new RuntimeException(e);
//                               }
//                           }
//                    );
//            return ResponseEntity.status(200).body(fileList);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
