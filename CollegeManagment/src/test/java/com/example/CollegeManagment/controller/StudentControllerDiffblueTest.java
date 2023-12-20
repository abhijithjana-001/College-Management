package com.example.CollegeManagment.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.Studentservice;
import com.example.CollegeManagment.service.impl.StudentServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {StudentController.class})
@ExtendWith(SpringExtension.class)
class StudentControllerDiffblueTest {
    @Autowired
    private StudentController studentController;

    @MockBean
    private Studentservice studentservice;

    /**
     * Method under test:
     * {@link StudentController#addstudent(String, MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddstudent() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        StudentController studentController = new StudentController(
                new StudentServiceImpl(mock(StudentRepo.class), mock(StudentMaptructConfig.class)));
        studentController.addstudent("alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }

    /**
     * Method under test:
     * {@link StudentController#listStudent(Integer, Integer, String)}
     */
    @Test
    void testListStudent() throws Exception {
        when(studentservice.listStudent(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/list");
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link StudentController#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent() throws Exception {
        when(studentservice.deletebyid(Mockito.<Long>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/delete/{id}", 1L);
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link StudentController#findstudent(Long)}
     */
    @Test
    void testFindstudent() throws Exception {
        when(studentservice.viewdetails(Mockito.<Long>any())).thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/{id}", 1L);
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test:
     * {@link StudentController#updateStudent(Long, String, MultipartFile)}
     */
    @Test
    void testUpdateStudent() throws Exception {
        when(studentservice.addorupdateStudent(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any()))
                .thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.put("/student/update").param("dto", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}
