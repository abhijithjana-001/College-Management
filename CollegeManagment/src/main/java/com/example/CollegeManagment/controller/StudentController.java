package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "student")
public class StudentController {
    @Autowired
    private Studentservice studentservice;

    @PostMapping("/add")
    public ResponseEntity<Responsedto<Student>> addstudent(@RequestBody Studentdto studentdto){
                 Responsedto responsedto=studentservice.addStudent(studentdto,null);
                 return ResponseEntity.ok(responsedto);
    }
    @GetMapping("/list")
    public ResponseEntity<Responsedto<List<Student>>> listStudent(){
        Responsedto responsedto=studentservice.listStudent();
        return ResponseEntity.ok(responsedto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Responsedto<List<Student>>> findstudent(@PathVariable Long id){
        Responsedto responsedto=studentservice.viewdetails(id);
        return ResponseEntity.ok(responsedto);
    }

     @DeleteMapping("/delete/{id}")
     public ResponseEntity<Responsedto<Student>> deleteStudent(@PathVariable Long id){
         Responsedto responsedto=studentservice.deletebyid(id);
         return ResponseEntity.ok(responsedto);
     }


     @PutMapping("/update/{id}")
     public ResponseEntity<Responsedto<Student>> updateStudent(@PathVariable Long id,@RequestBody Studentdto studentdto){
         Responsedto responsedto=studentservice.addStudent(studentdto,id);
         return ResponseEntity.ok(responsedto);
     }

}
