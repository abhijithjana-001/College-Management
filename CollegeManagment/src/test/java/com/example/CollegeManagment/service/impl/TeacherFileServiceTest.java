package com.example.CollegeManagment.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.*;
import com.example.CollegeManagment.repository.TeacherFileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
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
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
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
    // Arrange
        folder.create();
        File file = folder.newFile("testFile.jpg");
        Path path=Paths.get(file.getPath());


        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();

        teacherProfileImg.setTeacher(new Teacher());
        teacherProfileImg.setFilePath(file.getPath());
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName(file.getName());
        teacherProfileImg.setSize(file.length());
        teacherProfileImg.setType(Files.probeContentType(path));

        doReturn(Optional.of(teacherProfileImg)).when(teacherFileRepository).findByName(Mockito.<String>any());
    //  Act
        ImageData imageData = teacherFileService.findByName("testFile.jpg");

    //  Assert
        assertEquals(imageData.contenttype(),teacherProfileImg.getType());

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
