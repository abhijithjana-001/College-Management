package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.TeacherFileRepository;
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
import java.util.HashSet;
import java.util.Set;

@Service
public class TeacherFileService {
    @Value("${file.path}")
    private String uploadDir;

    @Autowired
    private TeacherFileRepository teacherFileRepository;

    public Responsedto upload(MultipartFile files[]){
        Set<TeacherProfileImg> teacherProfileImg=new HashSet<>();
        try{
            for(MultipartFile file:files) {
                Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
                file.transferTo(filePath.toFile());
                if (!teacherFileRepository.existsByName(file.getOriginalFilename())) {
                    teacherProfileImg.add(TeacherProfileImg.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .size(file.getSize())
                            .filePath(filePath.toString())
                            .created(LocalDateTime.now())
                            .build());
                } else {
                    throw new BadRequest("Image already exists.Duplication not allowed!");
                }
            }
            teacherFileRepository.saveAll(teacherProfileImg);
            return new Responsedto<>(true,files.length+
                    " File uploaded successfully",null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequest(e.getMessage());
        }
    }

    public ImageData findByName(String fileName) throws IOException{
        TeacherProfileImg teacherProfileImg=teacherFileRepository.findByName(fileName)
                .orElseThrow(()-> new ItemNotFound(" "+fileName+"not found"));
        Path path=Paths.get(teacherProfileImg.getFilePath());
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        byte[] image = Files.readAllBytes(path);
        return  new ImageData(contentType,image);
    }

    public Responsedto deleteFile(String fileName){
        TeacherProfileImg teacherProfileImg=teacherFileRepository.findByName(fileName)
                .orElseThrow(()->new ItemNotFound("Image "+fileName+" not found"));
        File file=new File(teacherProfileImg.getFilePath());
        if (file.exists() && file.delete()) {
            teacherFileRepository.delete(teacherProfileImg);
            return new Responsedto<>(true,"File deleted successfully",null);
        }else {
            return new Responsedto<>(false,"File deletion Failed",null);
        }
    }
}
