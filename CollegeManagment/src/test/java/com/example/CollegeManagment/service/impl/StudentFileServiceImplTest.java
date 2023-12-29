package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentFileServiceImplTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


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

     doReturn(false).when(studentProfileRepo).existsByName(anyString());


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
        doReturn(true).when(studentProfileRepo).existsByName(anyString());
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.upload(file));
    }

    @Test
    void testFindByName() throws IOException {
        // Arrange
        folder.create();
        File file = folder.newFile("myfile1.jpg");
        Path path=Paths.get(file.getPath());


        StudentProfileImg studentProfileImg = new StudentProfileImg();

        studentProfileImg.setStudent(new Student());
        studentProfileImg.setFilePath(file.getPath());
        studentProfileImg.setId(1L);
        studentProfileImg.setName(file.getName());
        studentProfileImg.setSize(file.length());
        studentProfileImg.setType(Files.probeContentType(path));



        doReturn(Optional.of(studentProfileImg)).when(studentProfileRepo).findByName(Mockito.<String>any());
//       act
        ImageData imageData = studentFileServiceImpl.findByName("myfile1.jpeg");
//         Assert



        assertEquals(imageData.contenttype(),studentProfileImg.getType());

    }

    @Test
    void testFindByNameItemNotFound() throws IOException {
        // Act and Assert
        assertThrows(ItemNotFound.class, () -> studentFileServiceImpl.findByName("file.txt"));
        verify(studentFileServiceImpl).findByName(Mockito.<String>any());
    }

    @Test
    void testDeletefile() throws IOException {
//     assign
        folder.create();
        File file = folder.newFile("myfile1.jpeg");



        StudentProfileImg studentProfileImg = new StudentProfileImg();

        studentProfileImg.setStudent(new Student());
        studentProfileImg.setFilePath(file.getPath());
        studentProfileImg.setId(1L);
        studentProfileImg.setName(file.getName());
        studentProfileImg.setSize(file.length());




        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(Optional.of(studentProfileImg));
        doNothing().when(studentProfileRepo).delete(any(StudentProfileImg.class));


        // Act
        studentFileServiceImpl.deletefile("sir.jpg");

//        assert
        verify(studentProfileRepo,times(1)).delete(any(StudentProfileImg.class));

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
