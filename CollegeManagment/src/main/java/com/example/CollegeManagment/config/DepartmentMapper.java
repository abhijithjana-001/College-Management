package com.example.CollegeManagment.config;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    Department toEntity(String departmentDto);


    default void updateEntity(DepartmentDto departmentDto, Department department) {
        department.setName(departmentDto.getName());
    }
}
