package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DepartmentFileControllerTest {

    @SpyBean
    private DepartmentFileController departmentFileController;

    @SpyBean
    private DepartmentFileService departmentFileService;

    @Test
    void getFile() throws Exception {
        // Arrange
        ImageData imageData = new ImageData("image/jpeg", "AXAXAXAX".getBytes("UTF-8"));
        doReturn(imageData).when(departmentFileService).findByName(anyString());

        // Act
        ResponseEntity<byte[]> file = departmentFileController.getFile("test.jpeg");

        // Assert
        assertEquals(HttpStatus.OK, file.getStatusCode());
        assertEquals("image/jpeg", Objects.requireNonNull(file.getHeaders().getContentType()).toString());
        assertEquals(file.getBody(), imageData.image());
    }

}
