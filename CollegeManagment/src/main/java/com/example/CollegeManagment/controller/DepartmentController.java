package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.DepartmentResponseDto;
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
    public ResponseEntity<DepartmentResponseDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        Department createdDepartment = departmentService.createDepartment(departmentDto);
        DepartmentResponseDto responseDto = converToDepartmentDto(createdDepartment);
        return ResponseEntity.ok(responseDto);
    }

//    @GetMapping(value = "listDepartment")
//    public List<DepartmentResponseDto> listAllDepartments(){
//        DepartmentResponseDto<List<DepartmentResponseDto>> departmentResponseDto = departmentService.findAllDepartments()
//        List<Department> departments = departmentResponseDto.getData();
//
//    }

    private DepartmentResponseDto converToDepartmentDto(Department department){
        if(department!=null){
            DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
            departmentResponseDto.setId(Math.toIntExact(department.getId()));
            departmentResponseDto.setName(department.getName());
            return departmentResponseDto;
        }
        return null;
    }
}
