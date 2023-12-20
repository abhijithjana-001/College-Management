package com.example.CollegeManagment.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.service.Teacherservice;

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

@ContextConfiguration(classes = {TeacherController.class})
@ExtendWith(SpringExtension.class)
class TeacherControllerDiffblueTest {
    @Autowired
    private TeacherController teacherController;

    @MockBean
    private Teacherservice teacherservice;

    /**
     * Method under test:
     * {@link TeacherController#addTeacher(String, MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddTeacher() throws IOException {
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

        // Arrange
        TeacherController teacherController = new TeacherController();

        // Act
        teacherController.addTeacher("Teacher Request DTO",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }

    /**
     * Method under test:
     * {@link TeacherController#findAll(Integer, Integer, String)}
     */
    @Test
    void testFindAll() throws Exception {
        // Arrange
        when(teacherservice.findAll(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teacher/teachers");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link TeacherController#delete(long)}
     */
    @Test
    void testDelete() throws Exception {
        // Arrange
        when(teacherservice.delete(anyLong())).thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/delete/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test:
     * {@link TeacherController#update(Long, String, MultipartFile)}
     */
    @Test
    void testUpdate() throws Exception {
        // Arrange
        when(teacherservice.createorupdate(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<MultipartFile>any()))
                .thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.put("/teacher/update").param("dto", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("id", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}
