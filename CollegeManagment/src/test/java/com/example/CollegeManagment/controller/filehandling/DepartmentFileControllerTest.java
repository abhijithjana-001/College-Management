package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class DepartmentFileControllerTest {

    @SpyBean
    private DepartmentFileController departmentFileController;

    @SpyBean
    private DepartmentFileService departmentFileService;



    @Test
    public void testGetFile() throws Exception {
        // Arrange
        ImageData imageData = new ImageData("image/jpeg", "AXAXAXAX".getBytes("UTF-8"));
        doReturn(imageData).when(departmentFileService).findByName(Mockito.<String>any());

        // Act
        ResponseEntity<byte[]> getFile = departmentFileController.getFile("test.jpeg");

        // Assert
        assertEquals(getFile.getStatusCode(), HttpStatus.OK);
        assertEquals(getFile.getHeaders().getContentType().toString(), "image/jpeg");
        assertEquals(getFile.getHeaders().getContentDisposition().toString(), "attachment; filename=\"test.jpeg\"");
        assertArrayEquals(getFile.getBody(), imageData.image());
    }
}
