package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;

import java.util.List;

public interface DepartmentService {

    Responsedto createOrUpdate(DepartmentDto departmentDto);


    Responsedto<List<Department>> findAllDepartments();


    Responsedto<Department> delete(Long id);
}
