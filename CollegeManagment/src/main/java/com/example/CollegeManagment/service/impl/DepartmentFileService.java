package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DepartmentFileService{
    @Value("${file.path}")
    private  String uploadDir;

    @Autowired
    private DepartmentFileRepository departmentFileRepository;
    public Responsedto upload(MultipartFile[] files){
        Set<DepartmentFileEntity> departmentFileEntities = new HashSet<>();
        try {

            for (MultipartFile file : files) {
                Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
                file.transferTo(filePath.toFile());
                departmentFileEntities.add(DepartmentFileEntity.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .size(file.getSize())
                        .filePath(filePath.toString())
                        .created(LocalDateTime.now())
                        .build());
            }
            departmentFileRepository.saveAll(departmentFileEntities);

            return new Responsedto<>(true, files.length + " File uploaded successfully!", null);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new BadRequest("File upload failed \n "+e.getMessage());
        }
    }

    public ImageData findByName(String name) throws  IOException {
        DepartmentFileEntity departmentFileEntity = (DepartmentFileEntity) departmentFileRepository.findByName(name).orElseThrow(()->new ItemNotFound("Image with name "+name+" not found"));
        Path path = Paths.get(departmentFileEntity.getFilePath());
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image = Files.readAllBytes(path);
        return  new ImageData(contentType,image);
    }

    public Responsedto deleteFile(String filename){
        DepartmentFileEntity departmentFileEntity = (DepartmentFileEntity) departmentFileRepository.findByName(filename).orElseThrow(()->new ItemNotFound("Image with name "+filename+" not found"));
        File file = new File(departmentFileEntity.getFilePath());
        if (file.exists() && file.delete()) {
            departmentFileRepository.delete(departmentFileEntity);
            return new Responsedto<>(true, "File delete successfully!", null);
        } else {
            return new Responsedto<>(false, "File delete successfully!", null);
        }
    }

}