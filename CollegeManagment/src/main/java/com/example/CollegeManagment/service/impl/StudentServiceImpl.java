package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl {
    @Autowired
    private StudentRepo studentRepo;
    public Responsedto<Student> addStudent(Studentdto studentdto) {
      Student student=new Student();
      student.setSname(studentdto.getName());
      student.setDepartment(studentdto.getDepartment());
      studentRepo.save(student);
      return new Responsedto<>(true,"student added successfull",student);
    }
}
