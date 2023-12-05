package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl {


        @Autowired
        TeacherRepo teacherRepo;
        @Autowired
        DepartmentRepo departmentRepo;


        public  Responsedto<Teacher> addTeacher(TeacherRequestDTO teacherRequestDTO) {
            Teacher teacher=new Teacher();
            teacher.setName(teacherRequestDTO.getName());
            teacher.setDepartment(teacherRequestDTO.getDepartment());
            Department department=departmentRepo.findById(teacherRequestDTO.getDepartment().getId()).orElseThrow(()->new ItemNotFound("Department not exist"));
            Set<Teacher> teachers=department.getTeachers();
            teachers.add(teacher);
            department.setTeachers(teachers);
            teacherRepo.save(teacher);
            return new Responsedto<>(true,"Added Successfully",teacher);
        }

        //@Override
        public Responsedto<List<Teacher>> findAll() {
            List<Teacher> teachers=teacherRepo.findAll();
            return new Responsedto<>(true,"Teachers List",teachers);
        }


       // @Override
        public Responsedto<Teacher> update(long id, TeacherRequestDTO teacherRequestDTO) {
            Teacher teacher = teacherRepo.findById(id).orElseThrow(()->new
                    ItemNotFound("Teacher not found with ID : "+id));

            teacher.setName(teacherRequestDTO.getName());
            teacher.setDepartment(teacherRequestDTO.getDepartment());
            teacherRepo.save(teacher);
            return new Responsedto<Teacher>(true, "Updated Successfully", teacher);
        }

        //@Override
        public Responsedto<Teacher> delete(long id) {
            teacherRepo.deleteById(id);
            return new Responsedto<Teacher>(true, "Deleted Successfully", null);
        }

      }
