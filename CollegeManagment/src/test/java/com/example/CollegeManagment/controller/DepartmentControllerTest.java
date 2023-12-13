package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
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
    void testFindAllDepartments() throws Exception {
        Department department1 = new Department(1L, "Computer Science");
        Department department2 = new Department(2L, "Physics");
        List<Department> mockedDepartments = Arrays.asList(department1, department2);
        Responsedto<List<Department>> mockedResponse = new Responsedto<>(true, "Departments retrieved successfully", mockedDepartments);

        when(departmentService.findAllDepartments()).thenReturn(mockedResponse);

        mockMvc.perform(get("/api/department/listDepartments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Departments retrieved successfully"))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[0].id").value(1L))
                .andExpect(jsonPath("$.result[0].name").value("Computer Science"))
                .andExpect(jsonPath("$.result[1].id").value(2L))
                .andExpect(jsonPath("$.result[1].name").value("Physics"));
    }

    @Test
    public void pagination_ReturnsPageOfDepartments() throws Exception {

        int offset = 0;
        int pageSize = 10;

        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1L, "Department 1"));
        departmentList.add(new Department(2L, "Department 2"));

        Page<Department> mockedPage = new PageImpl<>(departmentList);

        when(departmentService.findDepartmentWithPagination(offset, pageSize)).thenReturn(mockedPage);

        mockMvc.perform(get("/pagination/{offset}/{pageSize}", offset, pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Department 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Department 2"));
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
