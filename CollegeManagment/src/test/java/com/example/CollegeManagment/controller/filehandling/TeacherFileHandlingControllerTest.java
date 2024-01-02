package com.example.CollegeManagment.controller.filehandling;

import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.service.impl.TeacherFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeacherFileHandlingControllerTest {
    @SpyBean
    private TeacherFileHandlingController teacherFileHandlingController;

    @SpyBean
    private TeacherFileService teacherFileService;


    @Test
    void testDownloadImage() throws Exception {
        // Arrange
        ImageData imageData=new ImageData("image/jpg", "AXAXAXAX"
                .getBytes("UTF-8"));
        doReturn(imageData).when(teacherFileService).findByName(Mockito.anyString());

        //Act
        ResponseEntity<byte[]> downloadFile=teacherFileHandlingController.downloadImage("testImage.jpg");

        //Assert
        assertEquals(downloadFile.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(downloadFile.getHeaders().getContentType().toString(),"image/jpg");
        assertEquals(downloadFile.getBody(),imageData.image());
    }


}

