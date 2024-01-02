package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartmentFileControllerTest {

    @SpyBean
    private DepartmentFileController departmentFileController;

    @SpyBean
    private DepartmentFileService departmentFileService;



    @Test
    void testGetFile() throws IOException {
        //arrange
        DepartmentFileService departmentFileService = mock(DepartmentFileService.class);
        DepartmentFileController fileController = new DepartmentFileController();
        fileController.setDepartmentFileService(departmentFileService);

        String filename = "example.jpg";
        ImageData imageData = new ImageData("image/plain","toByte".getBytes(StandardCharsets.UTF_8));

        when(departmentFileService.findByName(filename)).thenReturn(imageData);

        //act
        ResponseEntity<byte[]> responseEntity = fileController.getFile(filename);

        verify(departmentFileService, times(1)).findByName(filename);

        // Assert response
        Assertions.assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());

        HttpHeaders headers = responseEntity.getHeaders();
        Assertions.assertNotNull(headers);
        assertEquals(MediaType.valueOf(imageData.contentType()), headers.getContentType());
        assertEquals("form-data; name=\"attachment\"; filename=\"example.jpg\"",
                headers.getContentDisposition().toString());

        byte[] responseBody = responseEntity.getBody();
        Assertions.assertNotNull(responseBody);
    }
}
