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
@ExtendWith(MockitoExtension.class)
class TeacherFileServiceTest {
    @Mock
    private TeacherFileRepository teacherFileRepository;

    @InjectMocks
    private TeacherFileService teacherFileService;

    @Mock
    private File fileMock;


    @Test
    void testUpload() {
        ReflectionTestUtils.setField(teacherFileService,"uploadDir","/Desktop/CollegeManagement/files/");
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        TeacherProfileImg expectedTeacherProfileImg = TeacherProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("\\Desktop\\CollegeManagement\\files\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(teacherFileRepository.existsByName(file.getOriginalFilename())).thenReturn(false);


        // Act
        TeacherProfileImg result = teacherFileService.upload(file);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTeacherProfileImg.getName(), result.getName());
        assertEquals(expectedTeacherProfileImg.getType(), result.getType());
        assertEquals(expectedTeacherProfileImg.getSize(), result.getSize());
        assertEquals(expectedTeacherProfileImg.getFilePath(), result.getFilePath());

        verify(teacherFileRepository, times(1)).existsByName(file.getOriginalFilename());
    }


    @Test
    void testFindByName() throws IOException {
        // Arrange
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> teacherFileService.findByName("file.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }


    @Test
    void testDeleteFile() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);

        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();
        teacherProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg.setFilePath("/directory/files.txt");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("Name");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setTeacher(teacher);
        teacherProfileImg.setType("Type");

        Optional<TeacherProfileImg> ofResult = Optional.of(teacherProfileImg);
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Responsedto actualDeleteFileResult = teacherFileService.deleteFile("file.txt");

        // Assert
        verify(teacherFileRepository).findByName(Mockito.<String>any());
        assertEquals("File deletion Failed", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
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
