package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.Studentservice;
import com.example.CollegeManagment.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)

@SpringBootTest
class StudentControllerTest {




    @SpyBean
    private Studentservice studentservice;

@SpyBean
private  StudentController studentController;

@SpyBean
    StudentRepo studentRepo;

    @Test
    void testAddstudent() throws IOException {

        // Arrange
        StudentServiceImpl studentservice = mock(StudentServiceImpl.class);
        when(studentservice.addorupdateStudent(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any()))
                .thenReturn(new Responsedto<>());
        StudentController studentController = new StudentController(studentservice);

        // Act
        ResponseEntity<Responsedto<Student>> actualAddstudentResult = studentController.addstudent(
                "alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

        // Assert
        verify(studentservice).addorupdateStudent(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any());
        assertEquals(200, actualAddstudentResult.getStatusCodeValue());
        assertTrue(actualAddstudentResult.hasBody());
        assertTrue(actualAddstudentResult.getHeaders().isEmpty());
    }


    @Test
    void testListStudent() throws Exception {
        // Arrange
        Page page=new PageImpl(Arrays.asList(new Student(),new Student()));

        doReturn(page).when(studentRepo).findAll(any(Pageable.class));
        // Act
        ResponseEntity<Responsedto<List<Student>>> listStudent = studentController.listStudent(1,2,"sname");

        // Assert

        assertEquals(HttpStatusCode.valueOf(200), listStudent.getStatusCode());
        assertTrue(listStudent.hasBody());
        assertEquals(2, listStudent.getBody().getResult().size());

    }
//
//    /**
//     * Method under test: {@link StudentController#deleteStudent(Long)}
//     */
    @Test
    void testDeleteStudent() throws Exception {
        // Arrange
        when(studentservice.deletebyid(Mockito.<Long>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/delete/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
//
//    /**
//     * Method under test: {@link StudentController#findstudent(Long)}
//     */
//    @Test
//    void testFindstudent() throws Exception {
//        // Arrange
//        when(studentservice.viewdetails(Mockito.<Long>any())).thenReturn(new Responsedto<>());
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/{id}", 1L);
//
//        // Act and Assert
//        MockMvcBuilders.standaloneSetup(studentController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("{}"));
//    }
//
//    /**
//     * Method under test:
//     * {@link StudentController#updateStudent(Long, String, MultipartFile)}
//     */
//    @Test
//    void testUpdateStudent() throws Exception {
//        // Arrange
//        when(studentservice.addorupdateStudent(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any()))
//                .thenReturn(new Responsedto<>());
//        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.put("/student/update").param("dto", "foo");
//        MockHttpServletRequestBuilder requestBuilder = paramResult.param("id", String.valueOf(1L));
//
//        // Act and Assert
//        MockMvcBuilders.standaloneSetup(studentController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("{}"));
//    }
}
