package com.example.CollegeManagment.config;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMaptructConfig {


    @Mapping(target = "student_id", ignore = true)
    Student toEntity(Studentdto studentdto);

}
