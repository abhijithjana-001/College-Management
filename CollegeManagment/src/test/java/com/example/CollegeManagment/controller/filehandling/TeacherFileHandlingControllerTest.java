package com.example.CollegeManagment.controller.filehandling;

import static org.mockito.Mockito.when;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.TeacherFileService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TeacherFileHandlingController.class})
@ExtendWith(SpringExtension.class)
class TeacherFileHandlingControllerTest {
    @Autowired
    private TeacherFileHandlingController teacherFileHandlingController;

    @MockBean
    private TeacherFileService teacherFileService;



    @Test
    void testDownloadImage() throws Exception {
        // Arrange
        when(teacherFileService.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teacher/file/{fileName}", "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }
    @Test
    void testDownloadImage2() throws Exception {
        // Arrange
        when(teacherFileService.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teacher/file/{fileName}", "foo.txt");
        requestBuilder.contentType("text/plain");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    @Test
    void testDeleteFile() throws Exception {
        // Arrange
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/file/delete/{filename}",
                "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testDeleteFile2() throws Exception {
        // Arrange
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/file/delete/{filename}",
                "foo.txt");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testDeleteFile3() throws Exception {
        // Arrange
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/file/delete/{filename}",
                "foo.txt");
        requestBuilder.contentType("text/plain");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testDeleteFile4() throws Exception {
        // Arrange
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/file/delete/{filename}",
                "foo.txt");
        requestBuilder.accept("https://example.org/example");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    @Test
    void testDeleteFile5() throws Exception {
        // Arrange
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/file/delete/{filename}",
                "foo.txt");
        requestBuilder.accept("");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testDeleteFile6() throws Exception {
        // Arrange
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teacher/file/delete/{filename}",
                "foo.txt");
        requestBuilder.content("AXAXAXAX".getBytes("UTF-8"));
        requestBuilder.contentType("text/plain");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(teacherFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}
