package com.example.CollegeManagment.config;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface StudentMaptructConfig {
    @Mapping(target = "student_id", ignore = true)
    Student toEntity(Studentdto studentdto);
}
