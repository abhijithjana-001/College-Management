package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import com.example.CollegeManagment.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "student")
public class StudentController {
    @Autowired
    private StudentServiceImpl studentservice;

    @PostMapping("/add")
    public ResponseEntity<Responsedto<Student>> addstudent(@RequestBody Studentdto studentdto){
                 Responsedto responsedto=studentservice.addStudent(studentdto);
                 return ResponseEntity.ok(responsedto);
    }
}
