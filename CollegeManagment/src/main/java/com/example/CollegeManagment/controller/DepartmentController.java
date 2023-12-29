package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(value = "/create")
    public ResponseEntity<Responsedto<Department>> addDepartment(@Valid @RequestParam(name = "dto") String departmentDto, @RequestParam(name = "file")MultipartFile file) {
        Responsedto<Department> responsedto = departmentService.createOrUpdate(departmentDto, file, null);
        return ResponseEntity.ok(responsedto);
    }

    @GetMapping(value = "/listDepartments")
    public ResponseEntity<Responsedto<List<Department>>> findAllDepartments(
            @RequestParam(value ="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value ="sortBy",defaultValue = "name",required = false) String sortBy
        ) {
        Responsedto<List<Department>> responsedto=departmentService.findAllDepartments(pageSize, pageNumber, sortBy);
        return ResponseEntity.ok(responsedto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Responsedto<Department>> update(@PathVariable(name = "id") Long id,
                                                          @Valid @RequestParam(name = "dto") String departmentDto,
                                                          @RequestParam(name = "file", required = false) MultipartFile file) {
        Responsedto<Department> Responsedto = departmentService.createOrUpdate(departmentDto, file, id);
        return ResponseEntity.ok(Responsedto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responsedto<Department>> delete(@PathVariable Long id) {
        Responsedto<Department> Responsedto = departmentService.delete(id);
        return ResponseEntity.ok(Responsedto);
    }


}
