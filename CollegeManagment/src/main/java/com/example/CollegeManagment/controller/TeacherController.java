package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherServiceImpl;

    @PostMapping("/addTeacher")
    public ResponseEntity<Responsedto<Teacher>> addTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        Responsedto<Teacher> Responsedto=teacherServiceImpl.addTeacher(teacherRequestDTO);
        return ResponseEntity.ok(Responsedto);
    }

}
