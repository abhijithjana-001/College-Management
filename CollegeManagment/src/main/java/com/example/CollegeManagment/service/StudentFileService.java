package com.example.CollegeManagment.service;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.StudentProfileImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentFileService {
    public StudentProfileImg upload(MultipartFile file);

    public ImageData findByName(String name) throws IOException;

    public Responsedto deletefile(String filename);


}
