package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "student")
public class StudentController {

    private final Studentservice studentservice;

    public StudentController( Studentservice studentservice){
        this.studentservice=studentservice;
    }

    @PostMapping("/add")
    public ResponseEntity<Responsedto<Student>> addstudent(@Valid @RequestParam(name = "dto") String studentdto, @RequestParam(name = "file")MultipartFile file){
                 Responsedto<Student> responsedto=studentservice.addorupdateStudent(studentdto,file,null);
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


     @PutMapping("/update")
     public ResponseEntity<Responsedto<Student>> updateStudent(@RequestParam(name = "id") Long id,@Valid  @RequestParam(name = "dto") String studentdto, @RequestParam(name = "file",required = false)MultipartFile file){
         Responsedto<Student> responsedto=studentservice.addorupdateStudent(studentdto,file,id);
         return ResponseEntity.ok(responsedto);
     }

}
