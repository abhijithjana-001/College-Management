package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/teachers")
    public ResponseEntity<Responsedto<List<Teacher>>> findAll(){
        Responsedto<List<Teacher>> all=teacherServiceImpl.findAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Responsedto<Teacher>> update(@RequestBody TeacherRequestDTO teacherRequestDTO,
                                                                @PathVariable long id) {
        Responsedto<Teacher> Responsedto = teacherServiceImpl.update(id, teacherRequestDTO);
        return ResponseEntity.ok(Responsedto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responsedto<Teacher>> delete(@PathVariable long id) {
        Responsedto<Teacher> Responsedto = teacherServiceImpl.delete(id);
        return ResponseEntity.ok(Responsedto);
    }

}
