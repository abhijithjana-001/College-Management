package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testAddDepartment() throws Exception {
        // Mock the input data
        DepartmentDto departmentRequestDto = new DepartmentDto();
        departmentRequestDto.setName("Test Department");

        // Mock the service response
        Department department = new Department();
        department.setId(1L);
        department.setName("Test Department");
        Responsedto<Department> responseDto = new Responsedto<>(department);

        when(departmentService.addDepartment(any(DepartmentDto.class))).thenReturn(responseDto);

        // Perform the HTTP POST request
        mockMvc.perform(post("/api/department/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departmentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Test Department"));
    }

    @Test
    public void testFindAllDepartments() throws Exception {
        // Mock the service response
        List<Department> departmentList = Arrays.asList(
                new Department(1L, "Department 1"),
                new Department(2L, "Department 2")
        );
        Responsedto<List<Department>> responseDto = new Responsedto<>((Department) departmentList);

        when(departmentService.findAllDepartments()).thenReturn(responseDto);

        // Perform the HTTP GET request
        mockMvc.perform(get("/api/department/listDepartments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("Department 1"))
                .andExpect(jsonPath("$.data[1].name").value("Department 2"));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        // Mock the input data
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Updated Department");

        // Mock the service response
        Department updatedDepartment = new Department(1L, "Updated Department");
        Responsedto<Department> responseDto = new Responsedto<>(updatedDepartment);

        when(departmentService.updateDepartment(eq(1L), any(DepartmentDto.class))).thenReturn(responseDto);

        // Perform the HTTP PUT request
        mockMvc.perform(put("/api/department/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Department"));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        // Mock the service response
        Department deletedDepartment = new Department(1L, "Deleted Department");
        Responsedto<Department> responseDto = new Responsedto<>(deletedDepartment);

        when(departmentService.delete(eq(1L))).thenReturn(responseDto);

        // Perform the HTTP DELETE request
        mockMvc.perform(delete("/api/department/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Deleted Department"));
    }

    // Helper method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}