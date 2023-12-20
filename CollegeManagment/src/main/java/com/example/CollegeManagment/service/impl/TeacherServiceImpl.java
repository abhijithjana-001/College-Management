package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.TeacherMapStruct;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.example.CollegeManagment.service.Teacherservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements Teacherservice {

        @Autowired
        TeacherRepo teacherRepo;
        @Autowired
        DepartmentRepo departmentRepo;

        @Autowired
        TeacherFileService teacherFileService;

        @Autowired
        TeacherMapStruct teacherMapStruct;

        @Autowired
        private ObjectMapper objectMapper;


        @Override
        public Responsedto<Teacher>createorupdate(Long id, String teacherdtoData, MultipartFile file) {
            String filename=null;
            TeacherRequestDTO teacherRequestDTO=null;
            try{
                teacherRequestDTO=objectMapper.readValue(teacherdtoData, TeacherRequestDTO.class);
            } catch(JsonProcessingException e){
                throw new RuntimeException(e);
            }

            Teacher teacher=teacherMapStruct.toEntity(teacherRequestDTO);
            TeacherProfileImg teacherProfileImg=teacherFileService.upload(file);
            if(id==null){
                teacher.setTid(new Teacher().getTid());
                teacher.setTeacherProfileImg(teacherProfileImg);

            }else{
                Boolean exist=teacherRepo.existsById(id);
                if(exist) {
                    filename=teacherRepo.findById(id).get().getTeacherProfileImg().getName();
                    teacher.setTid(id);
                    teacher.setTeacherProfileImg(new TeacherProfileImg());
                }
                else
                    throw  new ItemNotFound("Teacher not found with ID : "+id);
            }

            if(!teacherRepo.existsByPhno(teacher.getPhno()) ||
                    teacherRepo.findByPhno(teacherRequestDTO.getPhno()).get().getTid()==teacher.getTid())  {
                teacherRepo.save(teacher);
            }else{
                throw new BadRequest("Phone number already exists");
            }

            return new Responsedto<Teacher>(true, "Updated Successfully", teacher);
        }

    @Override
    public Responsedto<List<Teacher>> findAll(Integer pageSize, Integer pageNumber,String sort) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize, Sort.by(sort));
        Page<Teacher> pageTeacher=teacherRepo.findAll(pageable);
        List<Teacher> teachers=pageTeacher.getContent();
        return new Responsedto<>(true,"Teachers List",teachers);
    }


    @Override
        public Responsedto<Teacher> delete(long id) {
            teacherRepo.deleteById(id);
            return new Responsedto<Teacher>(true, "Deleted Successfully", null);
        }

      }
