package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;


    @Test
    void testAddDepartment() throws Exception {
        // Given
        DepartmentDto departmentDto = new DepartmentDto("Test Department");
        Department mockedDepartment = new Department(1L, "Test Department");
        Responsedto<Department> mockedResponse = new Responsedto<>(true, "Department added successfully", mockedDepartment);

        // Mock the behavior of departmentService.createOrUpdate
        when(departmentService.createOrUpdate(any(DepartmentDto.class), any()))
                .thenReturn(mockedResponse);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/department/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(departmentDto)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department added successfully"))
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.name").value("Test Department"));
    }

    @Test
    void testUpdateDepartment() throws Exception {
        long departmentId = 1L;
        DepartmentDto departmentDto = new DepartmentDto("Updated Department");

        Department mockedDepartment = new Department(departmentId, "Updated Department");
        Responsedto<Department> mockedResponse = new Responsedto<>(true, "Department updated successfully", mockedDepartment);

        when(departmentService.createOrUpdate(any(DepartmentDto.class), anyLong())).thenReturn(mockedResponse);

        ResultActions resultActions = mockMvc.perform(put("/api/department/update/{id}", departmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(departmentDto)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department updated successfully"))
                .andExpect(jsonPath("$.result.id").value(departmentId))
                .andExpect(jsonPath("$.result.name").value("Updated Department"));
    }


    @Test
    void findAllDepartments_ReturnsDepartmentList() throws Exception {
        // Arrange
        int pageSize = 5;
        int pageNumber = 0;
        String sortBy = "name";

        List<Department> departments = Collections.singletonList(new Department());
        Responsedto<List<Department>> response = new Responsedto<>(true, "Department List", departments);

        when(departmentService.findAllDepartments(pageSize, pageNumber, sortBy)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/listDepartments")
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("sortBy", sortBy))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department List"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").exists()) // Adjust based on your Department class properties
                .andExpect(jsonPath("$.data[0].name").exists()); // Adjust based on your Department class properties
    }

    @Test
    void testDeleteDepartment() throws Exception {
        long departmentId = 1L;
        Responsedto<Department> mockedResponse = new Responsedto<>(true, "Department deleted successfully", null);

        when(departmentService.delete(anyLong())).thenReturn(mockedResponse);

        mockMvc.perform(delete("/api/department/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department deleted successfully"))
                .andExpect(jsonPath("$.result").doesNotExist());

        verify(departmentService, times(1)).delete(anyLong());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
