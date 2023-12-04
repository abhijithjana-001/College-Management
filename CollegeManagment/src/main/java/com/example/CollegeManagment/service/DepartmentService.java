package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.DepartmentResponseDto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;

import java.util.List;

public interface DepartmentService {

    Department createDepartment(DepartmentDto departmentDto);

    DepartmentResponseDto<List<DepartmentResponseDto>> findAllDepartments();
}
