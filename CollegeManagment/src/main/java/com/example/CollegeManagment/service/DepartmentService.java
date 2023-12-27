package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DepartmentService {

    Responsedto<Department> createOrUpdate(String departmentDto, MultipartFile file, Long id);

    Responsedto<List<Department>> findAllDepartments(Integer pageSize, Integer pageNumber, String sortBy);

    Responsedto<Department> delete(Long id);
}
