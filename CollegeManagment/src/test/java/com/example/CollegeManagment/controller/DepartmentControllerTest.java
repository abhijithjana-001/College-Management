package com.example.CollegeManagment.controller;

import static org.mockito.Mockito.when;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DepartmentController.class})
@ExtendWith(SpringExtension.class)
class DepartmentControllerTest {
    @Autowired
    private DepartmentController departmentController;

    @MockBean
    private DepartmentService departmentService;

    /**
     * Method under test: {@link DepartmentController#findAllDepartments()}
     */
    @Test
    void testFindAllDepartments() throws Exception {
        when(departmentService.findAllDepartments()).thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/department/listDepartments");
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link DepartmentController#addDepartment(DepartmentDto)}
     */
    @Test
    void testAddDepartment() throws Exception {
        when(departmentService.createOrUpdate(Mockito.<DepartmentDto>any(), Mockito.<Long>any()))
                .thenReturn(new Responsedto());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(departmentDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/department/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link DepartmentController#delete(long)}
     */
    @Test
    void testDelete() throws Exception {
        when(departmentService.delete(Mockito.<Long>any())).thenReturn(new Responsedto<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/department/delete/{id}", 1L);
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link DepartmentController#update(long, DepartmentDto)}
     */
    @Test
    void testUpdate() throws Exception {
        when(departmentService.createOrUpdate(Mockito.<DepartmentDto>any(), Mockito.<Long>any()))
                .thenReturn(new Responsedto());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(departmentDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/department/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(departmentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}
