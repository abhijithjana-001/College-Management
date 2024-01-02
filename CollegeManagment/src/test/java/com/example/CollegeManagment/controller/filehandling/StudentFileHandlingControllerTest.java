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

import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentFileHandlingControllerTest {
    @SpyBean
    private StudentFileHandlingController studentFileHandlingController;

    @SpyBean
    private StudentFileServiceImpl studentFileServiceImpl;


    @Test
    void testGetfile() throws Exception {
        // Arrange
        ImageData imageData=new ImageData("image/jpeg", "AXAXAXAX".getBytes("UTF-8"));
      doReturn(imageData).when(studentFileServiceImpl).findByName(Mockito.<String>any());


        // Act
        ResponseEntity<byte[]> file = studentFileHandlingController.getfile("test.jpeg");
//        assert
      assertEquals(file.getStatusCode(), HttpStatusCode.valueOf(200));
    assertEquals(Objects.requireNonNull(file.getHeaders().getContentType()).toString(),"image/jpeg");
    assertEquals(file.getBody(),imageData.image());

    }


}
