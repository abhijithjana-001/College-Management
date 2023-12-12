package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.Studentservice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements Studentservice {
    private final StudentRepo studentRepo;
    private final StudentMaptructConfig studentMaptructConfig;

     public StudentServiceImpl(StudentRepo studentRepo,StudentMaptructConfig studentMaptructConfig){
                this.studentRepo=studentRepo;

                this.studentMaptructConfig=studentMaptructConfig;
     }
    @Override
    public Responsedto<Student> addorupdateStudent(Studentdto studentdto, Long id) {
        Student student=studentMaptructConfig.toEntity(studentdto);
        if (id==null)
            student.setStudent_id(new Student().getStudent_id());
         else {
             boolean exists= studentRepo.existsById(id);
             if(exists)
                 student.setStudent_id(id);
             else
                 throw new ItemNotFound("Student with id " + id + " is not found");

        }

         if(!studentRepo.existsByPhoneNum(studentdto.getPhoneNum())) {
             studentRepo.save(student);
        } else if (studentRepo.findByPhoneNum(studentdto.getPhoneNum()).get()
                 .getStudent_id().equals( student.getStudent_id())) {

             studentRepo.save(student);
         } else {
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
    public Responsedto<List<Student>> listStudent(Integer pagesize,Integer pagenumber,String sortby){
        Pageable pageable= PageRequest.of(pagenumber,pagesize,Sort.by(sortby).ascending());
        Page<Student> pagestudent = studentRepo.findAll(pageable);
        List<Student> students =pagestudent.getContent();

        return new Responsedto<>(true,"student list : "+students.size()+" students" ,students);
    }

}
