package com.example.CollegeManagment.config;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.entity.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentDto departmentDto);

    DepartmentDto toDto(Department department);

    List<DepartmentDto> departmentDto(List<Department> departments);
}
