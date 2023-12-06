package com.example.CollegeManagment.controller;


import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {

    @Mock
    private Studentservice studentservice;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testAddStudent() throws Exception {
        // Mocking data
        Studentdto studentdto = new Studentdto("John Doe", null);
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student added successful", new Student());

        when(studentservice.addStudent(studentdto)).thenReturn(mockedResponse);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/student/add")
                        .content("{\"name\":\"John Doe\",\"department\":null}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student added successful"));

        // Verify that the addStudent method was called
        verify(studentservice, times(1)).addStudent(studentdto);
    }

    @Test
    public void testListStudent() throws Exception {
        // Mocking data
        List<Student> mockedStudents = Arrays.asList(new Student(), new Student());
        Responsedto<List<Student>> mockedResponse = new Responsedto<>(true, "student list : 2 students", mockedStudents);

        when(studentservice.listStudent()).thenReturn(mockedResponse);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student list : 2 students"))
                .andExpect(jsonPath("$.result").isArray());

        // Verify that the listStudent method was called
        verify(studentservice, times(1)).listStudent();
    }

    @Test
    public void testFindStudent() throws Exception {
        // Mocking data
        Long studentId = 1L;
        Student mockedStudent = new Student();
        mockedStudent.setStudent_id(studentId);
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student added successful", mockedStudent);

        when(studentservice.viewdetails(studentId)).thenReturn(mockedResponse);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("student added successful"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.student_id").value(studentId));

        // Verify that the viewdetails method was called
        verify(studentservice, times(1)).viewdetails(studentId);

    }

    @Test
    public void testDeleteStudent() throws Exception {
        // Mocking data
        Long studentId = 1L;
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student delete successful", null);

        when(studentservice.deletebyid(studentId)).thenReturn(mockedResponse);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student delete successful"));

        // Verify that the deletebyid method was called
        verify(studentservice, times(1)).deletebyid(studentId);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        // Mocking data
        Long studentId = 1L;
        Studentdto updatedStudentDto = new Studentdto("Updated John Doe", null);
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student updated successful", new Student());

        when(studentservice.updateStudent(updatedStudentDto, studentId)).thenReturn(mockedResponse);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.put("/student/update/{id}", studentId)
                        .content("{\"name\":\"Updated John Doe\",\"department\":null}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student updated successful"));

        // Verify that the updateStudent method was called
        verify(studentservice, times(1)).updateStudent(updatedStudentDto, studentId);
    }
}