package com.example.CollegeManagment.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.TeacherFileRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;


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
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        TeacherProfileImg teacherProfileImg = TeacherProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("C:\\Users\\user0101\\Desktop\\CollegeManagement\\files\\"+file.getOriginalFilename())
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

        verify(teacherFileRepository, times(1)).existsByName(file.getOriginalFilename());
    }



    @Test
    void testFindByNameItemNotFound() throws IOException {
        // Arrange
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> teacherFileService.findByName("file.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }


    @Test
    void testDeleteFile() {
    MockMultipartFile file=new MockMultipartFile(
            "file","testFile.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
    TeacherProfileImg teacherProfileImg=TeacherProfileImg.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
            .size(file.getSize())
            .filePath("${file.path}\\"+file.getOriginalFilename())
            .created(LocalDateTime.now())
            .build();
        doReturn(Optional.of(teacherProfileImg)).when(teacherFileRepository).findByName(any(String.class));
        teacherFileService.deleteFile("image");
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
