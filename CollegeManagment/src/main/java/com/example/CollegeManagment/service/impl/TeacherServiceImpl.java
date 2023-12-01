package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.TeacherRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl {




        @Autowired
        TeacherRepo teacherRepo;




        public  Responsedto<Teacher> addTeacher(TeacherRequestDTO teacherRequestDTO) {


            Teacher teacher=new Teacher();
            teacher.setName(teacherRequestDTO.getName());
            teacher.setDepartment(teacherRequestDTO.getDepartment());
            teacherRepo.save(teacher);
            return new Responsedto<>(true,"Added Successfully",teacher);
        }

//        @Override
//        public EmployeeResponseDTO<List<Teacher>> findAll() {
//            List<Teacher> employees=employeeRepository.findAll();
//            return new EmployeeResponseDTO<>(true,"Employee List",employees);
//        }
//
//
//        @Override
//        public EmployeeResponseDTO<Teacher> update(long id, EmployeeRequestDTO employeeRequestDTO) {
//            Teacher employee = employeeRepository.findById(id).orElseThrow(()->new
//                    EmployeeNotFoundException("Employee not found with ID : "+id));
//
//            employee.setName(employeeRequestDTO.getName());
//            employee.setAge(employeeRequestDTO.getAge());
//            employee.setPhoneNo(employeeRequestDTO.getPhoneNo());
//            employeeRepository.save(employee);
//            return new EmployeeResponseDTO<Teacher>(true, "Updated Successfully", employee);
//        }
//
//        @Override
//        public EmployeeResponseDTO<Teacher> delete(long id) {
//
//            employeeRepository.deleteById(id);
//            return new EmployeeResponseDTO<Teacher>(true, "Deleted Successfully", null);
//        }
//
      }
