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

import com.example.CollegeManagment.service.StudentFileService;
import com.example.CollegeManagment.service.Studentservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final StudentFileService studentFileService;
    private final ObjectMapper objectMapper;
    public StudentServiceImpl(
             StudentRepo studentRepo,
             StudentMaptructConfig studentMaptructConfig,
             StudentFileService studentFileService,
             StudentProfileRepo studentProfileRepo,
             ObjectMapper objectMapper
             ){
                this.studentRepo=studentRepo;
                this.studentMaptructConfig=studentMaptructConfig;
                this.studentFileService=studentFileService;

                this.objectMapper=objectMapper;
              }

    @Override
    public Responsedto<Student> addorupdateStudent(String studentDtoData, MultipartFile file, Long id) {
         Studentdto studentdto=null;
        Student savedStudent=null;
        StudentProfileImg studentProfileImg=null;
        try {
            studentdto= objectMapper.readValue(studentDtoData,Studentdto.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Student student=studentMaptructConfig.toEntity(studentdto);
        if(file!=null){
             studentProfileImg = studentFileService.upload(file);
        }
        if (id==null){
            if(file==null)
                throw new BadRequest("file is null ");
            student.setStudentId(new Student().getStudentId());
             student.setProfileImg(studentProfileImg);
             savedStudent= create(student);
        }
         else {
             if(studentRepo.existsById(id)) {
                 student.setStudentId(id);
                if(file!= null){
                    student.setProfileImg(studentProfileImg);
                    savedStudent=  updateStudent(student,file,id);
                }
                else {
                    student.setProfileImg(studentRepo.findById(id).get().getProfileImg());
                    savedStudent=   updateStudent(student,null,id);
                }
             }
             else
                 throw new ItemNotFound("Student with id " + id + " is not found");
        }


        return new Responsedto<>(true,"student added or updated successful",savedStudent);
    }
    private Student create(Student student){
        if(!studentRepo.existsByPhoneNum(student.getPhoneNum())) {
           return studentRepo.save(student);
        }
        else
            throw new BadRequest("phone number already exist");
    }
    private   Student updateStudent(Student student,MultipartFile file,Long id)
    {
        String oldFile=studentRepo.findById(id).get().getProfileImg().getName();
        Student savedStudent=null;
        if(!studentRepo.existsByPhoneNum(student.getPhoneNum())) {
            savedStudent=  studentRepo.save(student);
        } else if (studentRepo.findByPhoneNum(student.getPhoneNum()).get()
                .getStudentId().equals( student.getStudentId())) {
            savedStudent =studentRepo.save(student);
        } else {
            throw new BadRequest("phone number already exist");
        }
        if(file!=null)
            studentFileService.deletefile(oldFile);
        return savedStudent;
    }
    @Override
    public  Responsedto<Student> viewdetails(Long id){
        Student student=studentRepo.findById(id)
                .orElseThrow(()->  new ItemNotFound("Student with id "+id +" is not found"));
        return new Responsedto<>(true,student.getSname()+" details:",student);
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
