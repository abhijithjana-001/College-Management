package com.example.CollegeManagment.controller;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.service.Teacherservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "teacher")
public class TeacherController {
    @Autowired
    Teacherservice teacherService;

    @PostMapping("/addTeacher")
    public ResponseEntity<Responsedto<Teacher>> addTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        Responsedto<Teacher> Responsedto= teacherService.createorupdate(null,teacherRequestDTO);
        return ResponseEntity.ok(Responsedto);
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


    @PutMapping("/update/{id}")
    public ResponseEntity<Responsedto<Teacher>> update(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO,
                                                                @PathVariable long id) {
        Responsedto<Teacher> Responsedto = teacherService.createorupdate(id, teacherRequestDTO);
        return ResponseEntity.ok(Responsedto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responsedto<Teacher>> delete(@PathVariable long id) {
        Responsedto<Teacher> Responsedto = teacherService.delete(id);
        return ResponseEntity.ok(Responsedto);
    }

}
