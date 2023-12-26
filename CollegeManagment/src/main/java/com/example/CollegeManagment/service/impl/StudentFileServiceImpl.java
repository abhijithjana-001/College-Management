package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import com.example.CollegeManagment.service.StudentFileService;
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
public class StudentFileServiceImpl implements StudentFileService {
    @Value("${file.path}")
    private  String uploadDir;
    private final StudentProfileRepo studentProfileRepo;
    public StudentFileServiceImpl(StudentProfileRepo studentProfileRepo)
    {
        this.studentProfileRepo=studentProfileRepo;
    }

    public StudentProfileImg  upload(MultipartFile file)  {
        isImage(file);

        StudentProfileImg studentProfileImgs;
               try{
                   Path directoryPath = Paths.get(uploadDir);
                   Files.createDirectories(directoryPath);
                Path filePath = Paths.get(uploadDir,file.getOriginalFilename());


                file.transferTo(filePath);

                if(!studentProfileRepo.existsByName(file.getOriginalFilename()))
                {
                     studentProfileImgs=   StudentProfileImg.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .size(file.getSize())
                             .link("/student/file/"+file.getOriginalFilename())
                            .filePath(filePath.toString())
                            .created(LocalDateTime.now())
                            .build();
                }
                else {
                    throw  new BadRequest("Duplicate entry , image already exists at location "+filePath);
                }

                return studentProfileImgs;
        } catch (IOException e) {
            e.printStackTrace();
            throw  new BadRequest("File upload failed \n"+e.getMessage());
        }
    }

    public ImageData findByName(String name) throws  IOException {
        StudentProfileImg studentprofile= studentProfileRepo.findByName(name).orElseThrow(()->new ItemNotFound("Image with name "+name+" not found"));
        Path path = Paths.get(studentprofile.getFilePath());
         String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image = Files.readAllBytes(path);
        return new ImageData(contentType,image);
    }
    public Responsedto deletefile(String filename){
        StudentProfileImg studentprofile= studentProfileRepo.findByName(filename).orElseThrow(()->new ItemNotFound("Image with name "+filename+" not found"));
        File file = new File(studentprofile.getFilePath());
        if (file.exists() && file.delete()) {
            System.out.println(file.exists());
            studentProfileRepo.delete(studentprofile);
            return new Responsedto<>(true, "File delete successfully!", null);
        }
        else throw new BadRequest("File delete failed!");
    }



    private Boolean isImage(MultipartFile file){
        if(file.getContentType().startsWith("image"))
            return true;
        else
            throw new BadRequest("uploaded file is not an image");
    }
}