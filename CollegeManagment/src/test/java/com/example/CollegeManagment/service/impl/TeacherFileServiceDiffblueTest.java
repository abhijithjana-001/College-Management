package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.TeacherFileRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {TeacherFileService.class})
@ExtendWith(SpringExtension.class)
class TeacherFileServiceDiffblueTest {
    @MockBean
    private TeacherFileRepository teacherFileRepository;

    @Autowired
    private TeacherFileService teacherFileService;

    /**
     * Method under test: {@link TeacherFileService#upload(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpload() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        MultipartFile file = null;

        // Act
        TeacherProfileImg actualUploadResult = this.teacherFileService.upload(file);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link TeacherFileService#findByName(String)}
     */
    @Test
    void testFindByName() throws IOException {
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> teacherFileService.findByName("foo.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TeacherFileService#findByName(String)}
     */
    @Test
    void testFindByName2() throws IOException {
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenThrow(new BadRequest("Msg"));
        assertThrows(BadRequest.class, () -> teacherFileService.findByName("foo.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TeacherFileService#deleteFile(String)}
     */
    @Test
    void testDeleteFile() {
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
        Responsedto actualDeleteFileResult = teacherFileService.deleteFile("foo.txt");
        verify(teacherFileRepository).findByName(Mockito.<String>any());
        assertEquals("File deletion Failed", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
    }

    /**
     * Method under test: {@link TeacherFileService#deleteFile(String)}
     */
    @Test
    void testDeleteFile2() {
        Optional<TeacherProfileImg> emptyResult = Optional.empty();
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> teacherFileService.deleteFile("foo.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TeacherFileService#deleteFile(String)}
     */
    @Test
    void testDeleteFile3() {
        when(teacherFileRepository.findByName(Mockito.<String>any())).thenThrow(new BadRequest("Msg"));
        assertThrows(BadRequest.class, () -> teacherFileService.deleteFile("foo.txt"));
        verify(teacherFileRepository).findByName(Mockito.<String>any());
    }
}
