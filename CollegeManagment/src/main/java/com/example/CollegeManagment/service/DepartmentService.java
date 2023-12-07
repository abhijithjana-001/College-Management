package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;

import java.util.List;

public interface DepartmentService {

    Responsedto createOrUpdate(DepartmentDto departmentDto);

    Responsedto addDepartment(DepartmentDto departmentDto);

    Responsedto<List<Department>> findAllDepartments();

    Responsedto<Department> updateDepartment(long id, DepartmentDto departmentDto);

    Responsedto<Department> delete(Long id);
}
