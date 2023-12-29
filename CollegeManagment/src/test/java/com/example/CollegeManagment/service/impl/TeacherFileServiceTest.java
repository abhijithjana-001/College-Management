package com.example.CollegeManagment.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.*;
import com.example.CollegeManagment.repository.TeacherFileRepository;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeacherFileServiceTest {
    @SpyBean
    private TeacherFileRepository teacherFileRepository;

    @SpyBean
    private TeacherFileService teacherFileService;


    @Test
    void testUpload() {

        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE,
                "test data".getBytes());

        TeacherProfileImg teacherProfileImg = TeacherProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("C:\\Users\\user0101\\Desktop\\CollegeManagement\\files\\"+file
                        .getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        doReturn(false).when(teacherFileRepository).existsByName(file.getOriginalFilename());


        // Act
        TeacherProfileImg result = teacherFileService.upload(file);

        // Assert
        assertNotNull(result);
        assertEquals(teacherProfileImg.getName(), result.getName());
        assertEquals(teacherProfileImg.getType(), result.getType());
        assertEquals(teacherProfileImg.getSize(), result.getSize());
        assertEquals(teacherProfileImg.getFilePath(), result.getFilePath());

        verify(teacherFileRepository, times(1)).existsByName
                (file.getOriginalFilename());
    }

    @Test
    void testFindByName() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file", "sir.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
        TeacherProfileImg teacherProfileImg = TeacherProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("${file.path}\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        Path mockedPath = Paths.get("${file.path}");


        byte[] mockedImageBytes = "mocked image data".getBytes();
        when(Files.readAllBytes(mockedPath)).thenReturn(mockedImageBytes);

        FileSystem mockedFileSystem = mock(FileSystem.class);
        when(mockedPath.getFileSystem()).thenReturn(mockedFileSystem);

        doReturn(Optional.of(teacherProfileImg)).when(teacherFileRepository).findByName(Mockito.<String>any());
//       act
        ImageData test = teacherFileService.findByName("test");
//         Assert
        assertEquals(test.contentType(),file.getContentType());
    }


    @Test
    void testFindByNameItemNotFound() throws IOException {
        assertThrows(ItemNotFound.class, () -> teacherFileService.findByName("file.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }


    @Test
    void testDeleteFile() {
        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();
        teacherProfileImg.setCreated
                (LocalDateTime.of(2010,10,10,5,5,55));
        teacherProfileImg.setTeacher(new Teacher());
        teacherProfileImg.setFilePath("${file.path}//testImage.jpg");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("sir.jpg");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setType("Type");

        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(Optional.of(teacherProfileImg));
        doNothing().when(teacherFileRepository).delete(teacherProfileImg);

        teacherFileService.deleteFile("testImage.jpg");
    }
    @Test
    void testDeleteFile2() {
        // Arrange
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> teacherFileService.deleteFile("file.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }

}
