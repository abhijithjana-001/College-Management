package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.Studentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements Studentservice {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private DepartmentRepo departmentRepo;
    public Responsedto<Student> addorupdateStudent(Studentdto studentdto, Long id) {
        Student student=
                          id==null?
                                  new Student()
                                   :
                                   studentRepo.findById(id).orElseThrow(()->new ItemNotFound("Student with id "+id +" is not found"));
        student.setSname(studentdto.getName());
        student.setDepartment(studentdto.getDepartment());
        studentRepo.save(student);
        return new Responsedto<>(true,"student added successful",student);
    }
    public  Responsedto<Student> viewdetails(Long id){
        Student student=studentRepo.findById(id)
                .orElseThrow(()->  new ItemNotFound("Student with id "+id +" is not found"));
        return new Responsedto<>(true,"student added successful",student);
    }

    public Responsedto deletebyid(Long id)
    {
        studentRepo.deleteById(id);
        return new Responsedto<>(true,"student delete successful",null);
    }

    public Responsedto<List<Student>> listStudent(){
       List<Student> students =studentRepo.findAll();

        return new Responsedto(true,"student list : "+students.size()+" students" ,students);
    }

}
