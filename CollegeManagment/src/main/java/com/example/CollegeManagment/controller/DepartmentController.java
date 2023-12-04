package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(value = "/createDepartment")
    public ResponseEntity<Responsedto<Department>> addDepartment(@RequestBody DepartmentDto departmentRequestDto) {
        Responsedto responsedto = departmentService.addDepartment(departmentRequestDto);
        return ResponseEntity.ok(responsedto);
    }


    @GetMapping(value = "/listDepartments")
    public ResponseEntity<Responsedto<List<Department>>> findAllDepartments(){
        Responsedto<List<Department>> all=departmentService.findAllDepartments();
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Responsedto<Department>> update(@RequestBody DepartmentDto departmentDto,@PathVariable long id) {
        Responsedto<Department> Responsedto = departmentService.updateDepartment(id, departmentDto);
        return ResponseEntity.ok(Responsedto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responsedto<Department>> delete(@PathVariable long id) {
        Responsedto<Department> Responsedto = departmentService.delete(id);
        return ResponseEntity.ok(Responsedto);
    }

    
}
