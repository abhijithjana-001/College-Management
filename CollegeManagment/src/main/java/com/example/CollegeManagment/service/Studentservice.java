package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Studentservice {
    public Responsedto<Student> addorupdateStudent(String studentdto,MultipartFile file,Long id);
    public  Responsedto<Student> viewdetails(Long id);
    public Responsedto deletebyid(Long id);
    public Responsedto<List<Student>> listStudent(Integer pagesize,Integer pagenumber,String sortby);

}
