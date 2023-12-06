package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TeacherControllerTest {

    @Mock
    private TeacherServiceImpl teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddTeacher() {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Test Teacher");

        Teacher savedTeacher = new Teacher();
        savedTeacher.setTid(1L);
        savedTeacher.setName("Test Teacher");

        when(teacherService.addTeacher(any())).thenReturn(new Responsedto<>(true, "Added Successfully", savedTeacher));

        ResponseEntity<Responsedto<Teacher>> responseEntity = teacherController.addTeacher(teacherRequestDTO);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(savedTeacher, responseEntity.getBody().getResult());
    }

    @Test
    void testFindAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setTid(1L);
        teacher1.setName("Teacher 1");
        teachers.add(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setTid(2L);
        teacher2.setName("Teacher 2");
        teachers.add(teacher2);

        when(teacherService.findAll()).thenReturn(new Responsedto<>(true, "Teachers List", teachers));

        ResponseEntity<Responsedto<List<Teacher>>> responseEntity = teacherController.findAll();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(teachers, responseEntity.getBody().getResult());
    }

    @Test
    void testUpdateTeacher() {
        long teacherId = 1L;
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Updated Teacher");

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setTid(teacherId);
        updatedTeacher.setName("Updated Teacher");

        when(teacherService.update(eq(teacherId), any())).thenReturn(new Responsedto<>(true, "Updated Successfully", updatedTeacher));

        ResponseEntity<Responsedto<Teacher>> responseEntity = teacherController.update(teacherRequestDTO, teacherId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(updatedTeacher, responseEntity.getBody().getResult());
    }

    @Test
    void testDeleteTeacher() {
        long teacherId = 1L;

        when(teacherService.delete(eq(teacherId))).thenReturn(new Responsedto<>(true, "Deleted Successfully", null));

        ResponseEntity<Responsedto<Teacher>> responseEntity = teacherController.delete(teacherId);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
