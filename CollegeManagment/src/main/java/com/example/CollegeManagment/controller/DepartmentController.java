package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(value = "/create")
    public ResponseEntity<Responsedto<Department>> addDepartment(@RequestBody DepartmentDto departmentRequestDto) {
        Responsedto responsedto = departmentService.createOrUpdate(departmentRequestDto, null);
        return ResponseEntity.ok(responsedto);
    }

    @GetMapping(value = "/listDepartments")
    public ResponseEntity<Responsedto<List<Department>>> findAllDepartments(
            @RequestParam(value ="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value ="sortBy",defaultValue = "name",required = false) String sortBy
        )
    {
        Responsedto<List<Department>> all=departmentService.findAllDepartments(pageSize, pageNumber, sortBy);
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Responsedto<Department>> update(@Valid @PathVariable long id, @RequestBody DepartmentDto departmentDto) {
        Responsedto<Department> Responsedto = departmentService.createOrUpdate(departmentDto, id);
        return ResponseEntity.ok(Responsedto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responsedto<Department>> delete(@PathVariable long id) {
        Responsedto<Department> Responsedto = departmentService.delete(id);
        return ResponseEntity.ok(Responsedto);
    }

    
}
