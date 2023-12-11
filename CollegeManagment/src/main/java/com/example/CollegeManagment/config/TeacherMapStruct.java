package com.example.CollegeManagment.config;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TeacherMapStruct {

    @Mappings(
            {
                    @Mapping(target="tid",ignore=true),
//                    @Mapping(source = "name", target = "name"),
//                    @Mapping(source = "phno",target = "phno"),
                    @Mapping(source = "department" ,target = "departments")
            }
    )
    Teacher toEntity(TeacherRequestDTO teacherRequestDTO);

}
