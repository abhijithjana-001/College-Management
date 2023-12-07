package com.example.CollegeManagment.controller;


import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.service.Studentservice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @MockBean
    private Studentservice studentservice;

    @InjectMocks
    private StudentController studentController;
@Autowired
    private MockMvc mockMvc;



    @Test
    public void testAddStudent() throws Exception {
        // Mocking data
        Studentdto studentdto = new Studentdto("John Doe", null);
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student added successful", new Student());

        when(studentservice.addorupdateStudent(studentdto,null)).thenReturn(mockedResponse);


        mockMvc.perform(MockMvcRequestBuilders.post("/student/add")
                        .content("{\"name\":\"John Doe\",\"department\":null}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student added successful"));


    }

    @Test
    public void testListStudent() throws Exception {

        List<Student> mockedStudents = Arrays.asList(new Student(), new Student());
        Responsedto<List<Student>> mockedResponse = new Responsedto<>(true, "student list : 2 students", mockedStudents);

        when(studentservice.listStudent()).thenReturn(mockedResponse);


        mockMvc.perform(MockMvcRequestBuilders.get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student list : 2 students"))
                .andExpect(jsonPath("$.result").isArray());


    }

    @Test
    public void testFindStudent() throws Exception {

        Long studentId = 1L;
        Student mockedStudent = new Student();
        mockedStudent.setStudent_id(studentId);
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student added successful", mockedStudent);

        when(studentservice.viewdetails(studentId)).thenReturn(mockedResponse);


        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("student added successful"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.student_id").value(studentId));



    }

    @Test
    public void testDeleteStudent() throws Exception {

        Long studentId = 1L;
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student delete successful", null);

        when(studentservice.deletebyid(studentId)).thenReturn(mockedResponse);


        mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student delete successful"));


        verify(studentservice, times(1)).deletebyid(studentId);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Long studentId = 1L;
        Studentdto updatedStudentDto = new Studentdto("Updated John Doe", null);
        Responsedto<Student> mockedResponse = new Responsedto<>(true, "student updated successful", new Student());

        when(studentservice.addorupdateStudent(updatedStudentDto, studentId)).thenReturn(mockedResponse);


        mockMvc.perform(MockMvcRequestBuilders.put("/student/update/{id}", studentId)
                        .content("{\"name\":\"Updated John Doe\",\"department\":null}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("student updated successful"));

    }
}
