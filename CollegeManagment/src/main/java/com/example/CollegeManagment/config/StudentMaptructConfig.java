package com.example.CollegeManagment.config;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMaptructConfig {
    StudentMaptructConfig studentmapper= Mappers.getMapper(StudentMaptructConfig.class);

    @Mappings(
            {
                    @Mapping(source = "sname", target = "sname"),
                    @Mapping(source = "department",target = "department"),
                    @Mapping(source = "phoneNum" ,target = "phoneNum")
            }
    )
    Student toEntity(Studentdto studentdto);
    @Mappings(
            {
                    @Mapping(source = "sname" , target = "sname"),
                    @Mapping(source = "department",target = "department"),
                    @Mapping(source = "phoneNum" ,target = "phoneNum")
            }
    )
    Studentdto toDto(Student student);
}
