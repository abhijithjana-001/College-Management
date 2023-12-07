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

    @MockBean
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;





    @Test
    public void testFindAllDepartments() throws Exception {
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
    public void testDeleteDepartment() throws Exception {
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
