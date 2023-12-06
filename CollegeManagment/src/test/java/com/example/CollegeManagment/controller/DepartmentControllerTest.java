package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.service.DepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform((RequestBuilder) post("/api/department/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(asJsonString(departmentRequestDto)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.data.name").value("Test Department"));
    }

    @Test
    public void testFindAllDepartments() throws Exception {
        // Mock the service response
        Department department1 = new Department(1L, "Department 1");
        Department department2 = new Department(2L, "Department 2");
        List<Department> departmentList = Arrays.asList(department1, department2);
        Responsedto<List<Department>> responseDto = new Responsedto<>(departmentList);

        when(departmentService.findAllDepartments()).thenReturn(responseDto);

        // Perform the HTTP GET request
        mockMvc.perform(get("/api/department/listDepartments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("Department 1"))
                .andExpect(jsonPath("$.data[1].name").value("Department 2"));
    }

    // Add similar tests for update and delete methods as needed

    // Helper method to convert object to JSON string
    private static MediaType asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
