package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.TeacherFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TeacherFileService {
    @Value("$file.path")
    private String uploadDir;

    @Autowired
    private TeacherFileRepository teacherFileRepository;

    public Responsedto upload(MultipartFile files[]){
        Set<TeacherProfileImg> teacherProfileImg=new HashSet<>();
        try{
            for(MultipartFile file:files){
                Path filePath= Paths.get(uploadDir,file.getOriginalFilename());
                file.transferTo(filePath.toFile());
                teacherProfileImg.add(TeacherProfileImg.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .size(file.getSize())
                        .filePath(filePath.toString())
                        .created(LocalDateTime.now())
                        .build());
            }
            teacherFileRepository.saveAll(teacherProfileImg);
            return new Responsedto<>(true,files.length+
                    " File uploaded successfully",null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Responsedto(false,"File uploaded failed",null);
        }

    }
}
