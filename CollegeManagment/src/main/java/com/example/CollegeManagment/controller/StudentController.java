package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "student")
public class StudentController {

    private final Studentservice studentservice;

    public StudentController( Studentservice studentservice){
        this.studentservice=studentservice;
    }

    @PostMapping("/add")
    public ResponseEntity<Responsedto<Student>> addstudent(@Valid @RequestBody Studentdto studentdto){
                 Responsedto<Student> responsedto=studentservice.addorupdateStudent(studentdto,null);
                 return ResponseEntity.ok(responsedto);
    }
    @GetMapping("/list")
    public ResponseEntity<Responsedto<List<Student>>> listStudent(
            @RequestParam(value ="pagenumber",defaultValue = "0",required = false) Integer pagenumber,
            @RequestParam(value ="pagesize",defaultValue = "5",required = false) Integer pagesize,
            @RequestParam(value ="sortby",defaultValue = "sname",required = false) String sortby
        )
    {

        Responsedto<List<Student>> responsedto=studentservice.listStudent( pagesize,pagenumber,sortby);
        return ResponseEntity.ok(responsedto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Responsedto<Student>> findstudent(@PathVariable Long id){
        Responsedto<Student> responsedto=studentservice.viewdetails(id);
        return ResponseEntity.ok(responsedto);
    }

     @DeleteMapping("/delete/{id}")
     public ResponseEntity<Responsedto<Student>> deleteStudent(@PathVariable Long id){
         Responsedto<Student> responsedto=studentservice.deletebyid(id);
         return ResponseEntity.ok(responsedto);
     }


     @PutMapping("/update/{id}")
     public ResponseEntity<Responsedto<Student>> updateStudent(@Valid @PathVariable Long id, @RequestBody Studentdto studentdto){
         Responsedto<Student> responsedto=studentservice.addorupdateStudent(studentdto,id);
         return ResponseEntity.ok(responsedto);
     }

}
