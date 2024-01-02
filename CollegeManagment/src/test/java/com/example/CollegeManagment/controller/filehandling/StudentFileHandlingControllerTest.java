package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.StudentFileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentFileHandlingControllerTest {
    @SpyBean
    private StudentFileHandlingController studentFileHandlingController;

    @SpyBean
    private StudentFileServiceImpl studentFileServiceImpl;


    @Test
    void testGetFile() throws Exception {
        // Arrange
        ImageData imageData=new ImageData("image/jpeg", "AXAPTA".getBytes(StandardCharsets.UTF_8));
      doReturn(imageData).when(studentFileServiceImpl).findByName(Mockito.<String>any());

      // Act
        ResponseEntity<byte[]> file = studentFileHandlingController.getfile("test.jpeg");
//        assert
      assertEquals(file.getStatusCode(), HttpStatusCode.valueOf(200));
    assertEquals(Objects.requireNonNull(file.getHeaders().getContentType()).toString(),"image/jpeg");
    assertEquals(file.getBody(),imageData.image());

    }


}
