package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.Studentservice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements Studentservice {
    private final StudentRepo studentRepo;
    private final StudentMaptructConfig studentMaptructConfig;

     public StudentServiceImpl(StudentRepo studentRepo,DepartmentRepo departmentRepo,StudentMaptructConfig studentMaptructConfig){
                this.studentRepo=studentRepo;

                this.studentMaptructConfig=studentMaptructConfig;
     }
    @Override
    public Responsedto<Student> addorupdateStudent(Studentdto studentdto, Long id) {
        Student student=null;
        if (id == null) {
          student=  new Student();
        } else {
             student=  studentRepo.findById(id).
                     orElseThrow(
                     () -> new ItemNotFound("Student with id " + id + " is not found")
             );
        }
        student= studentMaptructConfig.toEntity(studentdto);

        if(!studentRepo.existsByPhoneNum(studentdto.getPhoneNum())
                ||
                (studentRepo.findByPhoneNum(studentdto.getPhoneNum()).get()
                                .getStudent_id().equals( student.getStudent_id()))) {

                studentRepo.save(student);
        }
        else {
            throw new BadRequest("phone number already exist");
        }
        return new Responsedto<>(true,"student added or updated successful",student);
    }
    @Override
    public  Responsedto<Student> viewdetails(Long id){
        Student student=studentRepo.findById(id)
                .orElseThrow(()->  new ItemNotFound("Student with id "+id +" is not found"));
        return new Responsedto<>(true,"student added successful",student);
    }

    @Override
    public Responsedto deletebyid(Long id)
    {
        studentRepo.deleteById(id);
        return new Responsedto<>(true,"student delete successful",null);
    }

    @Override
    public Responsedto<List<Student>> listStudent(){
       List<Student> students =studentRepo.findAll();

        return new Responsedto<>(true,"student list : "+students.size()+" students" ,students);
    }

}
