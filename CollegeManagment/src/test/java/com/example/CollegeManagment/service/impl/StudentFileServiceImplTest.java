package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.ImageData;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    void testUpload() throws IOException {
//        arrange
        folder.create();
        File file = folder.newFile("file1.jpg");
        Path path=Paths.get(file.getPath());

        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setStudent(new Student());
        studentProfileImg.setFilePath(file.getPath());
        studentProfileImg.setId(1L);
        studentProfileImg.setName(file.getName());
        studentProfileImg.setSize(file.length());
        studentProfileImg.setType(Files.probeContentType(path));

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", file.getName(),Files.probeContentType(path), new FileInputStream(file));
        doReturn(false).when(studentProfileRepo).existsByName(anyString());

        // Act
        StudentProfileImg result = studentFileServiceImpl.upload(multipartFile);

        // Assert
        assertNotNull(result);
        assertEquals(studentProfileImg.getName(), result.getName());
        assertEquals(studentProfileImg.getType(), result.getType());
        assertEquals(studentProfileImg.getSize(), result.getSize());

        verify(studentProfileRepo, times(1)).existsByName(multipartFile.getOriginalFilename());
    }

    @Test
    void testUploadNotImage()  {

        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.json", MediaType.APPLICATION_JSON_VALUE, "test data".getBytes());
        // Act assert
        assertThrows(BadRequest.class,()->studentFileServiceImpl.upload(file));

    }
    @Test
    void uploadDuplicateEntryException()  {
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
        File file = folder.newFile("file1.jpg");
        Path path=Paths.get(file.getPath());


        StudentProfileImg studentProfileImg = new StudentProfileImg();

        studentProfileImg.setStudent(new Student());
        studentProfileImg.setFilePath(file.getPath());
        studentProfileImg.setId(1L);
        studentProfileImg.setName(file.getName());
        studentProfileImg.setSize(file.length());
        studentProfileImg.setType(Files.probeContentType(path));
        doReturn(Optional.of(studentProfileImg)).when(studentProfileRepo).findByName(anyString());
//       act
        ImageData imageData = studentFileServiceImpl.findByName("file1.jpeg");
//       Assert
        assertEquals(imageData.contentType(),studentProfileImg.getType());


    }

    @Test
    void testFindByNameItemNotFound() throws IOException {
        // Act and Assert
        assertThrows(ItemNotFound.class, () -> studentFileServiceImpl.findByName("file.txt"));
        verify(studentFileServiceImpl).findByName(anyString());
    }

    @Test
    void testDeleteFile() throws IOException {
//     assign
        folder.create();
        File file = folder.newFile("file1.jpeg");

        StudentProfileImg studentProfileImg = new StudentProfileImg();

        studentProfileImg.setStudent(new Student());
        studentProfileImg.setFilePath(file.getPath());
        studentProfileImg.setId(1L);
        studentProfileImg.setName(file.getName());
        studentProfileImg.setSize(file.length());

        when(studentProfileRepo.findByName(anyString())).thenReturn(Optional.of(studentProfileImg));
        doNothing().when(studentProfileRepo).delete(any(StudentProfileImg.class));
        // Act
        studentFileServiceImpl.deletefile("sir.jpg");
//        assert
        verify(studentProfileRepo,times(1)).delete(any(StudentProfileImg.class));
    }

    @Test
    void testDeleteFileException() {
        // Arrange
        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setName("test.jpg");
        studentProfileImg.setFilePath("/file/path");

        when(studentProfileRepo.findByName(any(String.class))).thenReturn(Optional.of(studentProfileImg));

        // Act and Assert
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile("test.jpg"));
        verify(studentProfileRepo).findByName(anyString());
    }

}
