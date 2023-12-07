package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.example.CollegeManagment.service.Teacherservice;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements Teacherservice {

        @Autowired
        TeacherRepo teacherRepo;
        @Autowired
        DepartmentRepo departmentRepo;


        @Override
        public Responsedto<List<Teacher>> findAll() {
            List<Teacher> teachers=teacherRepo.findAll();
            return new Responsedto<>(true,"Teachers List",teachers);
        }

        //demo
        @Override
        public Responsedto<Teacher>createorupdate(Long id, TeacherRequestDTO teacherRequestDTO) {
            Teacher teacher= id==null? new Teacher():
                     teacherRepo.findById(id).orElseThrow(()->new
                    ItemNotFound("Teacher not found with ID : "+id));


            teacher.setName(teacherRequestDTO.getName());
            teacher.setPhno(teacherRequestDTO.getPhno());
            teacher.setDepartments(teacherRequestDTO.getDepartment());
            if(!teacherRepo.existsByPhno(teacher.getPhno()) ||
                    teacherRepo.findByPhno(teacherRequestDTO.getPhno()).get().getTid()==teacher.getTid())  {
                teacherRepo.save(teacher);
            }else{
                throw new BadRequest("Phone number already exists : ");
            }

            return new Responsedto<Teacher>(true, "Updated Successfully", teacher);
        }


        @Override
        public Responsedto<Teacher> delete(long id) {
            teacherRepo.deleteById(id);
            return new Responsedto<Teacher>(true, "Deleted Successfully", null);
        }

      }
