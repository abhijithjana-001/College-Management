package com.example.CollegeManagment.controller.filehandling;

import static org.mockito.Mockito.when;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.StudentFileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {StudentFileHandlingController.class})
@ExtendWith(SpringExtension.class)
class StudentFileHandlingControllerDiffblueTest {
    @Autowired
    private StudentFileHandlingController studentFileHandlingController;

    @MockBean
    private StudentFileService studentFileService;

    /**
     * Method under test: {@link StudentFileHandlingController#getfile(String)}
     */
    @Test
    void testGetfile() throws Exception {
        when(studentFileService.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/file/{filename}", "foo.txt");
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    /**
     * Method under test:
     * {@link StudentFileHandlingController#handleFileUpload(MultipartFile[])}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleFileUpload() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        StudentFileHandlingController studentFileHandlingController = new StudentFileHandlingController();
        studentFileHandlingController.handleFileUpload(
                new MultipartFile[]{new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))});
    }

    /**
     * Method under test: {@link StudentFileHandlingController#getfile(String)}
     */
    @Test
    void testGetfile2() throws Exception {
        when(studentFileService.findByName(Mockito.<String>any()))
                .thenReturn(new ImageData("text/plain", "AXAXAXAX".getBytes("UTF-8")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/file/{filename}", "foo.txt");
        requestBuilder.contentType("text/plain");
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    /**
     * Method under test:
     * {@link StudentFileHandlingController#handleFileDelete(String)}
     */
    @Test
    void testHandleFileDelete() throws Exception {
        when(studentFileService.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test:
     * {@link StudentFileHandlingController#handleFileDelete(String)}
     */
    @Test
    void testHandleFileDelete2() throws Exception {
        when(studentFileService.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.contentType("text/plain");
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test:
     * {@link StudentFileHandlingController#handleFileDelete(String)}
     */
    @Test
    void testHandleFileDelete3() throws Exception {
        when(studentFileService.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.accept("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    /**
     * Method under test:
     * {@link StudentFileHandlingController#handleFileDelete(String)}
     */
    @Test
    void testHandleFileDelete4() throws Exception {
        when(studentFileService.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.accept("");
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test:
     * {@link StudentFileHandlingController#handleFileDelete(String)}
     */
    @Test
    void testHandleFileDelete5() throws Exception {
        when(studentFileService.deletefile(Mockito.<String>any())).thenReturn(new Responsedto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/file/delete/{filename}",
                "foo.txt");
        requestBuilder.content("AXAXAXAX".getBytes("UTF-8"));
        requestBuilder.contentType("text/plain");
        MockMvcBuilders.standaloneSetup(studentFileHandlingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}
