package com.example.CollegeManagment.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
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
    void testAddDepartment() {
        // Arrange
        String departmentDtoJson = "{\"name\":\"YourDepartmentName\",\"otherField\":\"otherValue\"}";
        MultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "file content".getBytes());

        // Create a sample Department object for the response
        Department createdDepartment = new Department();
        createdDepartment.setId(1L);
        createdDepartment.setName("YourDepartmentName");
        // Add other fields as needed

        Responsedto<Department> responseDto = new Responsedto<>(createdDepartment);

        // Mock the service method to return the expected response
        when(departmentService.createOrUpdate(any(), any(), any())).thenReturn(responseDto);

        // Act
        ResponseEntity<Responsedto<Department>> responseEntity = departmentController.addDepartment(departmentDtoJson, file);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue()); // Assuming OK status code
        assertEquals(responseDto, responseEntity.getBody()); // Check if the response body matches the expected value
        // Add more assertions if needed based on the expected behavior of your controller
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
