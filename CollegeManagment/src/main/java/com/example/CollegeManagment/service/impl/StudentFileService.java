package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentFileService {
    @Value("${file.path}")
    private  String uploadDir;

    @Autowired
    private StudentProfileRepo studentProfileRepo;
    public Responsedto upload(MultipartFile files[]){
        Set<StudentProfileImg> studentProfileImgs=new HashSet<>();
        try {

            for (MultipartFile file : files) {
                Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
                file.transferTo(filePath.toFile());
                 studentProfileImgs.add(StudentProfileImg.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .size(file.getSize())
                        .filePath(filePath.toString())
                        .created(LocalDateTime.now())
                        .build());
            }
            studentProfileRepo.saveAll(studentProfileImgs);

            return new Responsedto<>(true, files.length + " File uploaded successfully!", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Responsedto<>(false, "File uploaded failed!", null);
        }
    }

    public Path findByName(String name) throws  IOException {
        StudentProfileImg studentprofile= studentProfileRepo.findByName(name).orElseThrow(()->new ItemNotFound("Image with name "+name+" not found"));
        Path path = Paths.get(studentprofile.getFilePath());
       return path;

    }
    public Responsedto deletefile(String filename){
        StudentProfileImg studentprofile= studentProfileRepo.findByName(filename).orElseThrow(()->new ItemNotFound("Image with name "+filename+" not found"));
        File file = new File(studentprofile.getFilePath());
        if (file.exists() && file.delete()) {

            return new Responsedto<>(true, "File delete successfully!", null);
        } else {
            return new Responsedto<>(false, "File delete successfully!", null);
        }
    }

}
