package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
class DepartmentControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    void addDepartmentTest() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("department");

        Responsedto<Department> responseDTO = new Responsedto<>(true, "Added Successfully", new Department());

        when(departmentService.addDepartment(departmentDto)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/department/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Added Successfully"));
    }

    @Test
    void findAllDepartmentTest() throws Exception {
        List<Department> departments = Arrays.asList(new Department(), new Department());
        Responsedto<List<Department>> responseDTO = new Responsedto<>(true, "Department List", departments);

        when(departmentService.findAllDepartments()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/department/listDepartments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department List"));
    }

    @Test
    void updateDepartmentTest() throws Exception {
        long departmentId = 1L;
        DepartmentDto requestDTO = new DepartmentDto();
        requestDTO.setName("Updated Department");

        Responsedto<Department> responseDTO = new Responsedto<>(true, "Updated Successfully", new Department());

        when(departmentService.updateDepartment(departmentId, requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/department/updateDepartment/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Updated Successfully"));
    }

    @Test
    void deleteDepartmentTest() throws Exception {
        long departmentId = 1L;
        Responsedto<Department> responseDTO = new Responsedto<>(true, "Deleted Successfully", null);

        when(departmentService.delete(departmentId)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/department/deleteDepartment/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Deleted Successfully"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
