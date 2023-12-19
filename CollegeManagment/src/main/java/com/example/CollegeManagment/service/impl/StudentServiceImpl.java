package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import com.example.CollegeManagment.repository.StudentRepo;

import com.example.CollegeManagment.service.Studentservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class StudentServiceImpl implements Studentservice {
    private final StudentRepo studentRepo;
    private final StudentMaptructConfig studentMaptructConfig;

    @Autowired
    private  StudentFileServiceImpl studentFileService;

    @Autowired
    private StudentProfileRepo studentProfileRepo;

    @Autowired
    private ObjectMapper objectMapper;

     public StudentServiceImpl(StudentRepo studentRepo,StudentMaptructConfig studentMaptructConfig){
                this.studentRepo=studentRepo;

                this.studentMaptructConfig=studentMaptructConfig;
     }
    @Override
    public Responsedto<Student> addorupdateStudent(String studentdtodata, MultipartFile file, Long id) {
         Studentdto studentdto=null;
        try {
            studentdto= objectMapper.readValue(studentdtodata,Studentdto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Student student=studentMaptructConfig.toEntity(studentdto);
        StudentProfileImg studentProfileImg=studentFileService.upload(file);

        if (id==null){
            student.setStudentId(new Student().getStudentId());
             student.setProfileImg(studentProfileImg);
        }
         else {

             if(studentRepo.findById(id).isPresent()) {
                 student.setStudentId(id);
                 studentFileService.deletefile(studentRepo.findById(id).get().getProfileImg().getName());

                 student.setProfileImg(studentProfileImg);

             }
             else
                 throw new ItemNotFound("Student with id " + id + " is not found");

        }

         if(!studentRepo.existsByPhoneNum(studentdto.getPhoneNum())) {
             studentRepo.save(student);
        } else if (studentRepo.findByPhoneNum(studentdto.getPhoneNum()).get()
                 .getStudentId().equals( student.getStudentId())) {

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
