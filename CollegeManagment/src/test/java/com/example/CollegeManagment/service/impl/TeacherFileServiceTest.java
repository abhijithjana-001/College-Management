package com.example.CollegeManagment.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.TeacherFileRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {TeacherFileService.class})
@ExtendWith(SpringExtension.class)
class TeacherFileServiceTest {
    @MockBean
    private TeacherFileRepository teacherFileRepository;

    @Autowired
    private TeacherFileService teacherFileService;

    /**
     * Method under test: {@link TeacherFileService#upload(MultipartFile)}
     */
    @Test
    void testUpload() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        TeacherProfileImg expectedTeacherProfileImg = TeacherProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("/path/to/uploaded/file")
                .created(LocalDateTime.now())
                .build();

        when(teacherFileRepository.existsByName(file.getOriginalFilename())).thenReturn(false);
        when(teacherFileRepository.save(any(TeacherProfileImg.class))).thenReturn(expectedTeacherProfileImg);

        // Act
        TeacherProfileImg result = teacherFileService.upload(file);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTeacherProfileImg.getName(), result.getName());
        assertEquals(expectedTeacherProfileImg.getType(), result.getType());
        assertEquals(expectedTeacherProfileImg.getSize(), result.getSize());
        assertEquals(expectedTeacherProfileImg.getFilePath(), result.getFilePath());
        assertEquals(expectedTeacherProfileImg.getCreated(), result.getCreated());

        verify(teacherFileRepository, times(1)).existsByName(file.getOriginalFilename());
        verify(teacherFileRepository, times(1)).save(any(TeacherProfileImg.class));
    }


    @Test
    void testFindByName() throws IOException {
        // Arrange
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> teacherFileService.findByName("foo.txt"));
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
        teacherProfileImg.setFilePath("/directory/foo.txt");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("Name");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setTeacher(teacher);
        teacherProfileImg.setType("Type");

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTeacherProfileImg(teacherProfileImg);
        teacher2.setTid(1L);

        TeacherProfileImg teacherProfileImg2 = new TeacherProfileImg();
        teacherProfileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg2.setFilePath("/directory/foo.txt");
        teacherProfileImg2.setId(1L);
        teacherProfileImg2.setName("Name");
        teacherProfileImg2.setSize(3L);
        teacherProfileImg2.setTeacher(teacher2);
        teacherProfileImg2.setType("Type");
        Optional<TeacherProfileImg> ofResult = Optional.of(teacherProfileImg2);
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Responsedto actualDeleteFileResult = teacherFileService.deleteFile("foo.txt");

        // Assert
        verify(teacherFileRepository).findByName(Mockito.<String>any());
        assertEquals("File deletion Failed", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
    }


    @Test
    void testDeleteFile2() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);

        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();
        teacherProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg.setFilePath("/directory/foo.txt");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("Name");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setTeacher(teacher);
        teacherProfileImg.setType("Type");

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTeacherProfileImg(teacherProfileImg);
        teacher2.setTid(1L);
        TeacherProfileImg teacherProfileImg2 = mock(TeacherProfileImg.class);
        when(teacherProfileImg2.getFilePath()).thenReturn("/directory/foo.txt");
        doNothing().when(teacherProfileImg2).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(teacherProfileImg2).setFilePath(Mockito.<String>any());
        doNothing().when(teacherProfileImg2).setId(Mockito.<Long>any());
        doNothing().when(teacherProfileImg2).setName(Mockito.<String>any());
        doNothing().when(teacherProfileImg2).setSize(Mockito.<Long>any());
        doNothing().when(teacherProfileImg2).setTeacher(Mockito.<Teacher>any());
        doNothing().when(teacherProfileImg2).setType(Mockito.<String>any());
        teacherProfileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg2.setFilePath("/directory/foo.txt");
        teacherProfileImg2.setId(1L);
        teacherProfileImg2.setName("Name");
        teacherProfileImg2.setSize(3L);
        teacherProfileImg2.setTeacher(teacher2);
        teacherProfileImg2.setType("Type");
        Optional<TeacherProfileImg> ofResult = Optional.of(teacherProfileImg2);
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Responsedto actualDeleteFileResult = teacherFileService.deleteFile("foo.txt");

        // Assert
        verify(teacherProfileImg2).getFilePath();
        verify(teacherProfileImg2).setCreated(Mockito.<LocalDateTime>any());
        verify(teacherProfileImg2).setFilePath(Mockito.<String>any());
        verify(teacherProfileImg2).setId(Mockito.<Long>any());
        verify(teacherProfileImg2).setName(Mockito.<String>any());
        verify(teacherProfileImg2).setSize(Mockito.<Long>any());
        verify(teacherProfileImg2).setTeacher(Mockito.<Teacher>any());
        verify(teacherProfileImg2).setType(Mockito.<String>any());
        verify(teacherFileRepository).findByName(Mockito.<String>any());
        assertEquals("File deletion Failed", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
    }


    @Test
    void testDeleteFile3() {
        // Arrange
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> teacherFileService.deleteFile("foo.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }
}
