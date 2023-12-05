package com.example.CollegeManagment.controller;


import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Studentservice studentservice;

    @InjectMocks
    private StudentController studentController;

    @Test
    void addStudent() throws Exception {
        Studentdto studentdto = new Studentdto(/* provide necessary details */);
        Responsedto<Student> expectedResponse = new Responsedto<>(true, "Student added successfully", new Student(/* provide expected student details */));

        when(studentservice.addStudent(studentdto)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentdto)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));

        verify(studentservice, times(1)).addStudent(studentdto);
        verifyNoMoreInteractions(studentservice);
    }

    @Test
    void listStudent() throws Exception {
        List<Student> studentList = Collections.singletonList(new Student(/* provide expected student details */));
        Responsedto<List<Student>> expectedResponse = new Responsedto<>(true, "List of students retrieved successfully", studentList);

        when(studentservice.listStudent()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));

        verify(studentservice, times(1)).listStudent();
        verifyNoMoreInteractions(studentservice);
    }

    @Test
    void findStudent() throws Exception {
        Long studentId = 1L;
        Responsedto<Student> expectedResponse = new Responsedto<>(true, "Student details retrieved successfully",new Student());

        when(studentservice.viewdetails(studentId)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));

        verify(studentservice, times(1)).viewdetails(studentId);
        verifyNoMoreInteractions(studentservice);
    }

    @Test
    void deleteStudent() throws Exception {
        Long studentId = 1L;
        Responsedto<Student> expectedResponse = new Responsedto<>(true, "Student deleted successfully", new Student());

        when(studentservice.deletebyid(studentId)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));

        verify(studentservice, times(1)).deletebyid(studentId);
        verifyNoMoreInteractions(studentservice);
    }

    @Test
    void updateStudent() throws Exception {
        Long studentId = 1L;
        Studentdto updatedStudentDto = new Studentdto(/* provide updated details */);
        Responsedto<Student> expectedResponse = new Responsedto<>(true, "Student updated successfully", new Student(/* provide updated details */));

        when(studentservice.updateStudent(updatedStudentDto, studentId)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/student/update/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedStudentDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));

        verify(studentservice, times(1)).updateStudent(updatedStudentDto, studentId);
        verifyNoMoreInteractions(studentservice);
    }

    // Utility method to convert objects to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
