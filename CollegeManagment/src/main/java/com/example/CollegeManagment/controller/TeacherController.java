package com.example.CollegeManagment.controller;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.service.Teacherservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "teacher")
public class TeacherController {

   private final Teacherservice teacherService;

    public TeacherController(Teacherservice teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/addTeacher")
    public ResponseEntity<Responsedto<Teacher>> addTeacher(@Valid @RequestParam(name = "dto")
                           String teacherRequestDTO, @RequestParam(name="file")MultipartFile file) {
        Responsedto<Teacher> responsedto= teacherService.createorupdate(null,teacherRequestDTO,file);
        return ResponseEntity.ok(responsedto);
    }

    @GetMapping("/teachers")
    public ResponseEntity<Responsedto<List<Teacher>>> findAll(
        @RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
        @RequestParam(value="pageSize",defaultValue = "5",required = false) Integer pageSize,
        @RequestParam(value = "sort",defaultValue = "name",required = false)String sort)
        {
            Responsedto<List<Teacher>> all= teacherService.findAll(pageSize,pageNumber,sort);
            return ResponseEntity.ok(all);
        }
    @GetMapping("/{id}")
    public ResponseEntity<Responsedto<Teacher>> findTeacher(@PathVariable Long id){
        Responsedto<Teacher> responsedto=teacherService.viewDetails(id);
        return ResponseEntity.ok(responsedto);
    }

    @PutMapping("/update")
    public ResponseEntity<Responsedto<Teacher>> update(@RequestParam(name = "id") Long id,@Valid @RequestParam(name = "dto")
                   String teacherRequestDTO, @RequestParam(name="file",required = false)MultipartFile file) {
        Responsedto<Teacher> responsedto = teacherService.createorupdate(id,teacherRequestDTO,file);
        return ResponseEntity.ok(responsedto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responsedto<Teacher>> delete(@PathVariable long id) {
        Responsedto<Teacher> Responsedto = teacherService.delete(id);
        return ResponseEntity.ok(Responsedto);
    }

}
