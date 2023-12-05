package com.example.CollegeManagment.service;


import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class TeacherServiceTest {


        @Mock
        private TeacherRepo teacherRepo;

        @Mock
        private DepartmentRepo departmentRepo;

        @InjectMocks
        private TeacherServiceImpl teacherService;

        @Test
        void TeacherServiceTest_addTeacher() {
            Teacher teacher = new Teacher();
            teacher.setName("John");
            teacher.setDepartments(new HashSet<>());

            TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
            teacherRequestDTO.setName("John");
            teacherRequestDTO.setDepartment(new HashSet<>());

            when(teacherRepo.save(Mockito.any(Teacher.class))).thenReturn(teacher);

            Responsedto<Teacher> teacherResponseDTO = teacherService.addTeacher(teacherRequestDTO);

            verify(teacherRepo, times(1)).save(Mockito.any(Teacher.class));

            assertAll(() -> {
                assertThat(Responsedto).isNotNull();
                assertThat(Responsedto.getResult()).isEqualTo(teacher);
            });
        }

        @Test
        void TeacherServiceTest_findAll() {
            Teacher teacher = new Teacher();
            teacher.setName("John");
            teacher.setDepartments(new HashSet<>());

            List<Teacher> teacherList = Arrays.asList(teacher, teacher);

            when(teacherRepo.findAll()).thenReturn(teacherList);

            Responsedto<List<Teacher>> teacherResponseDTO = teacherService.findAll();

            verify(teacherRepo, times(1)).findAll();

            assertAll(() -> {
                assertThat(teacherResponseDTO).isNotNull();
                assertThat(teacherResponseDTO.getResult()).hasSize(2);
            });
        }

        @Test
        void TeacherServiceTest_update() {
            Long id = 1L;
            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setName("John");
            teacher.setDepartments(new HashSet<>());

            TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
            teacherRequestDTO.setName("Jane");
            teacherRequestDTO.setDepartment(new HashSet<>());

            when(teacherRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(teacher));
            when(teacherRepo.save(Mockito.any(Teacher.class))).thenReturn(teacher);

            Responsedto<Teacher> teacherResponseDTO = teacherService.update(id, teacherRequestDTO);

            verify(teacherRepo, times(1)).findById(Mockito.any(Long.class));
            verify(teacherRepo, times(1)).save(Mockito.any(Teacher.class));

            assertAll(() -> {
                assertThat(teacherResponseDTO).isNotNull();
                assertThat(teacherResponseDTO.getResult()).isEqualTo(teacher);
            });
        }

        @Test
        void TeacherServiceTest_delete() {
            Long id = 1L;
            doNothing().when(teacherRepo).deleteById(id);
            teacherService.delete(id);
            verify(teacherRepo, times(1)).deleteById(id);
        }
    }




