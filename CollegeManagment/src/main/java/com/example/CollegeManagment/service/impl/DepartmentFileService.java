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

@Service
public class DepartmentFileService{
    @Value("${file.path}")
    private  String uploadDir;

    private final DepartmentFileRepository departmentFileRepository;
    @Autowired
    public DepartmentFileService(DepartmentFileRepository departmentFileRepository) {
        this.departmentFileRepository = departmentFileRepository;
    }

    public DepartmentFileEntity upload(MultipartFile file) throws IOException {
       DepartmentFileEntity departmentFileEntities ;

                Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
                file.transferTo(filePath.toFile());
                departmentFileEntities = DepartmentFileEntity.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .size(file.getSize())
                        .filePath(filePath.toString())
                        .created(LocalDateTime.now())
                        .build();

            departmentFileRepository.save(departmentFileEntities);

            return departmentFileEntities;
    }

    public ImageData findByName(String name) throws  IOException {
        DepartmentFileEntity departmentFileEntity = departmentFileRepository.findByName(name)
                .orElseThrow(()->new ItemNotFound("Image with name "+name+" not found"));
        Path path = Paths.get(departmentFileEntity.getFilePath());
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image = Files.readAllBytes(path);
        return  new ImageData(contentType,image);
    }

    public void deleteFile(String filename) {
        DepartmentFileEntity departmentFileEntity = departmentFileRepository.findByName(filename).orElseThrow(() -> new ItemNotFound("Image with name " + filename + " not found"));
        File file = new File(departmentFileEntity.getFilePath());
        if (file.exists() && file.delete()) {
            departmentFileRepository.delete(departmentFileEntity);
        } else {
            throw new BadRequest("File deletion failed");
        }
    }

}