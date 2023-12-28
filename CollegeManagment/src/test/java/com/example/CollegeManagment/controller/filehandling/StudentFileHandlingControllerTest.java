package com.example.CollegeManagment.controller.filehandling;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.service.impl.StudentFileServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {StudentFileHandlingController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentFileHandlingController.class)
class StudentFileHandlingControllerTest {
    @Autowired
    private StudentFileHandlingController studentFileHandlingController;

    @MockBean
    private StudentFileServiceImpl studentFileServiceImpl;


    @Test
    void testGetfile() throws Exception {
        // Arrange
        when(studentFileServiceImpl.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/file/{filename}", "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }


}
