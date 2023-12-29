package com.example.CollegeManagment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.TeacherFileRepository;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.example.CollegeManagment.service.Teacherservice;
import com.example.CollegeManagment.service.impl.TeacherServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeacherControllerTest {
    @SpyBean
    private TeacherController teacherController;

    @SpyBean
    private Teacherservice teacherservice;

    @SpyBean
    private TeacherRepo teacherRepo;


    @Test
    void testAddTeacher() throws IOException {
        TeacherServiceImpl teacherService = mock(TeacherServiceImpl.class);
        when(teacherService.createorupdate( Mockito.<Long>any(),Mockito.<String>any(), Mockito.<MultipartFile>any()))
                .thenReturn(new Responsedto<>());
        TeacherController teacherController = new TeacherController(teacherService);

        // Act
        ResponseEntity<Responsedto<Teacher>> actualAddTeacher = teacherController.addTeacher(
                "alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AkeMal".getBytes("UTF-8"))));

        // Assert
        verify(teacherService).createorupdate(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<MultipartFile>any());
        assertEquals(200, actualAddTeacher.getStatusCodeValue());
        assertTrue(actualAddTeacher.hasBody());
        assertTrue(actualAddTeacher.getHeaders().isEmpty());
    }


    @Test
    void testFindAll() throws Exception {
        // Arrange
       Page page=new PageImpl(Arrays.asList(new Teacher(),new Teacher(),new Teacher()));
       doReturn(page).when(teacherRepo).findAll(any(Pageable.class));

       //Act
       ResponseEntity<Responsedto<List<Teacher>>> teacherList = teacherController.findAll(1,3,"name");

       //Assert
        assertEquals(HttpStatusCode.valueOf(200),teacherList.getStatusCode());
        assertTrue(teacherList.hasBody());
        assertEquals(3,teacherList.getBody().getResult().size());
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
    void testFindTeacher() throws Exception {
        // Arrange
        when(teacherservice.viewDetails(Mockito.<Long>any())).thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teacher/{id}", 1L);

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
