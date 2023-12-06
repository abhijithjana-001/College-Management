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
    public void testAddDepartment() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto("Computer Science");
        Department mockedDepartment = new Department(1L, "Computer Science");
        Responsedto<Department> mockedResponse = new Responsedto<>(true, "Department added successfully", mockedDepartment);

        when(departmentService.addDepartment(any(DepartmentDto.class))).thenReturn(mockedResponse);

        mockMvc.perform(post("/api/department/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department added successfully"))
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.name").value("Computer Science"));

        verify(departmentService, times(1)).addDepartment(any(DepartmentDto.class));
    }

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

        verify(departmentService, times(1)).findAllDepartments();
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto("Computer Science");
        Department existingDepartment = new Department(1L, "Updated Computer Science");
        Responsedto<Department> mockedResponse = new Responsedto<>(true, "Department updated successfully", existingDepartment);

        when(departmentService.updateDepartment(anyLong(), any(DepartmentDto.class))).thenReturn(mockedResponse);

        mockMvc.perform(put("/api/department/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department updated successfully"))
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.name").value("Updated Computer Science"));

        verify(departmentService, times(1)).updateDepartment(anyLong(), any(DepartmentDto.class));
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

    // Utility method to convert an object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
