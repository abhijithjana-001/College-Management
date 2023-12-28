package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.controller.StudentController;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private Studentservice studentservice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent() {
        // Arrange
        String studentDto = "your student DTO JSON";
        MultipartFile file = mock(MultipartFile.class);
        Responsedto<Student> expectedResponse = new Responsedto<>(true, "Student added successfully", new Student());
        when(studentservice.addorupdateStudent(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Responsedto<Student>> responseEntity = studentController.addstudent(studentDto, file);

        // Assert
        verify(studentservice).addorupdateStudent(studentDto, file, null);
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testListStudents() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "sname";
        Responsedto<List<Student>> expectedResponse = new Responsedto<>(true, "Student list: 1 student", Collections.singletonList(new Student()));
        when(studentservice.listStudent(pageSize, pageNumber, sortBy)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Responsedto<List<Student>>> responseEntity = studentController.listStudent(pageNumber, pageSize, sortBy);

        // Assert
        verify(studentservice).listStudent(pageSize, pageNumber, sortBy);
        assertEquals(expectedResponse, responseEntity.getBody());
    }

}
