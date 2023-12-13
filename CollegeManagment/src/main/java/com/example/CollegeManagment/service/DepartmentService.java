package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {

    Responsedto createOrUpdate(DepartmentDto departmentDto, Long id);

    Responsedto<List<Department>> findAllDepartments(Integer pageSize, Integer pageNumber, String sortBy);

    Responsedto<Department> delete(Long id);
}
