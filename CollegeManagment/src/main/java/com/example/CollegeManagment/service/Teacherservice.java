package com.example.CollegeManagment.service;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Teacherservice {

    //public Responsedto<Teacher> addTeacher(Long id,TeacherRequestDTO teacherRequestDTO);

    public Responsedto<Teacher>createorupdate(Long id, String teacherdtoData, MultipartFile file);

    public Responsedto<List<Teacher>> findAll(Integer pageSize,Integer pageNumber,String sort);

    public  Responsedto<Teacher> viewDetails(Long id);

  //  public Responsedto<Teacher> update(long id, TeacherRequestDTO teacherRequestDTO);

    public Responsedto<Teacher> delete(long id);
}
