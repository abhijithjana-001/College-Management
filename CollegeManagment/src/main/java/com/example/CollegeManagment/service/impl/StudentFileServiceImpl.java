package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import com.example.CollegeManagment.service.StudentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class StudentFileServiceImpl implements StudentFileService {

    @Value("${file.path}")
    private  String uploadDir;

    @Autowired
    private  StudentProfileRepo studentProfileRepo;


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
            throw  new BadRequest("File upload failed \n"+e.getMessage());
        }
    }

    public ImageData findByName(String name) throws  IOException {
        StudentProfileImg studentProfile= studentProfileRepo.findByName(name).orElseThrow(()->new ItemNotFound("Image with name "+name+" not found"));
        Path path = Paths.get(studentProfile.getFilePath());
         String contentType = Files.probeContentType(path);
         byte[] image = Files.readAllBytes(path);
        return new ImageData(contentType,image);
    }
    public void deletefile(String filename){
        StudentProfileImg studentProfile= studentProfileRepo.findByName(filename).orElseThrow(()->new ItemNotFound("Image with name "+filename+" not found"));
        File file = new File(studentProfile.getFilePath());
        if (file.exists() && file.delete()) {
            System.out.println(file.exists());
            studentProfileRepo.delete(studentProfile);
       }
        else throw new BadRequest("File delete failed!");
    }



    private void isImage(MultipartFile file) {
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image"))

            throw new BadRequest("uploaded file is not an image");
    }
}