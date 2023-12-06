package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;

import java.util.List;

public interface Teacherservice {

    public Responsedto<Teacher> addTeacher(TeacherRequestDTO teacherRequestDTO);

    public Responsedto<List<Teacher>> findAll();

    public Responsedto<Teacher> update(long id, TeacherRequestDTO teacherRequestDTO);

    public Responsedto<Teacher> delete(long id);
}
