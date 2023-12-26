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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {StudentFileServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StudentFileServiceImplTest {

    @Autowired
    private StudentFileServiceImpl studentFileServiceImpl;

    @MockBean
    private StudentProfileRepo studentProfileRepo;

    @MockBean
    private File fileMock;


    @Test
    void testUpload()  {

        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        StudentProfileImg expectedStudentProfileImg = StudentProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("${file.path}\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(studentProfileRepo.existsByName(file.getOriginalFilename())).thenReturn(false);
        when(studentProfileRepo.save(any(StudentProfileImg.class))).thenReturn(expectedStudentProfileImg);

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
        String filename = "test.jpg";
        String filePath = "/path/to/test.jpg";



        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setName(filename);
        studentProfileImg.setFilePath(filePath);

//        when(File.class).thenReturn(new File());
        when(studentProfileRepo.findByName(any(String.class))).thenReturn(Optional.of(studentProfileImg));
        when(fileMock.exists()).thenReturn(true);
        when(fileMock.delete()).thenReturn(true);
        doNothing().when(studentProfileRepo).delete(any(StudentProfileImg.class));



        // Act
        Responsedto response = studentFileServiceImpl.deletefile(filename);

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("File delete successfully!", response.getMessage());
        assertNull(response.getResult());

        // Verify interactions
        verify(fileMock).exists();
        verify(fileMock).delete();
        verify(studentProfileRepo).delete(studentProfileImg);
    }

    @Test
    void testDeletefileException() {
        // Arrange
        String filename = "test.jpg";
        String filePath = "/path/to/test.jpg";



        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setName(filename);
        studentProfileImg.setFilePath(filePath);

        when(studentProfileRepo.findByName(any(String.class))).thenReturn(Optional.of(studentProfileImg));
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.delete()).thenReturn(false);


        // Act and Assert
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile("foo.txt"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }


}
