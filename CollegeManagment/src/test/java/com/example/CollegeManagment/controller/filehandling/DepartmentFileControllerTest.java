package com.example.CollegeManagment.controller.filehandling;

import static org.mockito.Mockito.when;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DepartmentFileController.class})
@ExtendWith(SpringExtension.class)
class DepartmentFileControllerTest {
    @Autowired
    private DepartmentFileController departmentFileController;

    @MockBean
    private DepartmentFileService departmentFileService;

    @Test
    void testGetFile() throws Exception {
        // Arrange
        when(departmentFileService.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/department/file/{filename}", "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(departmentFileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

}
