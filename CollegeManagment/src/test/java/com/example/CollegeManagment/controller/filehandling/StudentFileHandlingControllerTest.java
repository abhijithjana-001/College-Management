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


    @Test
    void testHandleFileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "AXAXAXAX".getBytes(StandardCharsets.UTF_8));

        when(studentFileServiceImpl.upload(file)).thenReturn(new StudentProfileImg()); // Mock the upload method response

        // Act and Assert
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentFileHandlingController).build();

        mockMvc.perform(multipart("/student/file/upload").file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testGetfile2() throws Exception {
        // Arrange
        when(studentFileServiceImpl.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", new byte[]{-56, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/file/{filename}", "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("ÈXAXAXAX"));
    }


    @Test
    void testGetfile3() throws Exception {
        // Arrange
        when(studentFileServiceImpl.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/file/{filename}", "foo.txt");
        requestBuilder.contentType("text/plain");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }


    @Test
    void testHandleFileDelete() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testHandleFileDelete2() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testHandleFileDelete3() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.contentType("text/plain");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testHandleFileDelete4() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.accept("https://example.org/example");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }


    @Test
    void testHandleFileDelete5() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.accept("");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testHandleFileDelete6() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.content("AXAXAXAX".getBytes("UTF-8"));
        requestBuilder.contentType("text/plain");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testHandleFileDelete7() throws Exception {
        // Arrange
        when(studentFileServiceImpl.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}", "",
                "Uri Variables");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}
