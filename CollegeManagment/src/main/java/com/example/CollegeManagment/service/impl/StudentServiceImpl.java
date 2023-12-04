package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentServiceImpl {
    @Autowired
    TeacherRepo teacherRepo;
    public Responsedto<Teacher> addTeacher(TeacherRequestDTO teacherRequestDTO) {


        Teacher teacher=new Teacher();
        teacher.setName(teacherRequestDTO.getName());
        teacher.setDepartment(teacherRequestDTO.getDepartment());
        teacherRepo.save(teacher);
        return new Responsedto<>(true,"Added Successfully",teacher);
    }
}
