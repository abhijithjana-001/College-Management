package com.example.CollegeManagment.controller;

import static org.mockito.Mockito.when;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.service.DepartmentService;

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

@ContextConfiguration(classes = {DepartmentController.class})
@ExtendWith(SpringExtension.class)
class DepartmentControllerDiffblueTest {
    @Autowired
    private DepartmentController departmentController;

    @MockBean
    private DepartmentService departmentService;

    /**
     * Method under test:
     * {@link DepartmentController#addDepartment(String, MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddDepartment() throws IOException {
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
        DepartmentController departmentController = new DepartmentController();

        // Act
        departmentController.addDepartment("alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }

    /**
     * Method under test:
     * {@link DepartmentController#findAllDepartments(Integer, Integer, String)}
     */
    @Test
    void testFindAllDepartments() throws Exception {
        // Arrange
        when(departmentService.findAllDepartments(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/department/listDepartments");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link DepartmentController#delete(Long)}
     */
    @Test
    void testDelete() throws Exception {
        // Arrange
        when(departmentService.delete(Mockito.<Long>any())).thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/department/delete/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test:
     * {@link DepartmentController#update(Long, String, MultipartFile)}
     */
    @Test
    void testUpdate() throws Exception {
        // Arrange
        when(departmentService.createOrUpdate(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any()))
                .thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/department/update/{id}", 1L)
                .param("dto", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}
