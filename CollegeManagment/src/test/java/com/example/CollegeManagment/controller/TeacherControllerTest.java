package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.service.Teacherservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {TeacherController.class})
@ExtendWith(SpringExtension.class)
class TeacherControllerTest {
    @Autowired
    private TeacherController teacherController;

    @MockBean
    private Teacherservice teacherservice;


    @Test
     void testAddTeacher() throws IOException {
        TeacherServiceImpl teacherservice = mock(TeacherServiceImpl.class);
        when(teacherservice.createorupdate( Mockito.<Long>any(),Mockito.<String>any(), Mockito.<MultipartFile>any()))
                .thenReturn(new Responsedto<>());
        TeacherController teacherController = new TeacherController(teacherservice);

        // Act
        ResponseEntity<Responsedto<Teacher>> actualAddteacher = teacherController.addTeacher(
                "alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

        // Assert
        verify(teacherservice).createorupdate(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<MultipartFile>any());
        assertEquals(200, actualAddteacher.getStatusCodeValue());
        assertTrue(actualAddteacher.hasBody());
        assertTrue(actualAddteacher.getHeaders().isEmpty());
    }


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
