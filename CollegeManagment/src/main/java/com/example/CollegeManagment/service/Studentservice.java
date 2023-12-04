package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;

import java.util.List;

public interface Studentservice {
    public Responsedto<Student> addStudent(Studentdto studentdto);
    public  Responsedto<Student> viewdetails(Long id);
    public Responsedto deletebyid(Long id);
    public Responsedto<List<Student>> listStudent();

    public Responsedto<Student> updateStudent(Studentdto studentdto,Long id);
}
