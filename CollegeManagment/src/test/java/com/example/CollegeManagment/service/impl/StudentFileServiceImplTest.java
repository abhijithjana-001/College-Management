package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.*;
import com.example.CollegeManagment.repository.StudentProfileRepo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentFileServiceImplTest {

    @SpyBean
    private StudentFileServiceImpl studentFileServiceImpl;

    @SpyBean
    private StudentProfileRepo studentProfileRepo;



    @Test
    void testUpload()  {

        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        StudentProfileImg expectedStudentProfileImg = StudentProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("C:\\Users\\user453\\Desktop\\CollegeManagement\\files\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(studentProfileRepo.existsByName(file.getOriginalFilename())).thenReturn(false);


        // Act
        StudentProfileImg result = studentFileServiceImpl.upload(file);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStudentProfileImg.getName(), result.getName());
        assertEquals(expectedStudentProfileImg.getType(), result.getType());
        assertEquals(expectedStudentProfileImg.getSize(), result.getSize());
        assertEquals(expectedStudentProfileImg.getFilePath(), result.getFilePath());


        verify(studentProfileRepo, times(1)).existsByName(file.getOriginalFilename());

    }

    @Test
    void testUploadNotImage()  {

        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.json", MediaType.APPLICATION_JSON_VALUE, "test data".getBytes());
        // Act assert
        assertThrows(BadRequest.class,()->studentFileServiceImpl.upload(file));

    }
@Test
void uploadDuplicateEntryException() throws IOException {
//arrange
    MockMultipartFile file = new MockMultipartFile(
            "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
    when(studentProfileRepo.existsByName(any(String.class))).thenReturn(true);
    assertThrows(BadRequest.class, () -> studentFileServiceImpl.upload(file));

}


    @Test
    void testFindByName() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
        StudentProfileImg studentProfileImg = StudentProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("${file.path}\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(Optional.of(studentProfileImg));
//       act
        ImageData test = studentFileServiceImpl.findByName("test");
//         Assert
        assertEquals(test.contenttype(),file.getContentType());
        assertEquals(test.image().length,file.getSize());
    }

    @Test
    void testDeletefile(){
//     assign
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
        StudentProfileImg studentProfileImg = StudentProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("${file.path}\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        doReturn(Optional.of(studentProfileImg)).when(studentProfileRepo).findByName(any(String.class));



        // Act
        studentFileServiceImpl.deletefile("image");



    }

    @Test
    void testDeleteFileException() {
        // Arrange
        String filename = "test.jpg";
        String filePath = "/path/to/test.jpg";



        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setName(filename);
        studentProfileImg.setFilePath(filePath);

        when(studentProfileRepo.findByName(any(String.class))).thenReturn(Optional.of(studentProfileImg));



        // Act and Assert
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile(filename));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }


}
