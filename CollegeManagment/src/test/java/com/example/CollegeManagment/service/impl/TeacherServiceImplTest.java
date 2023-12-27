package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.TeacherMapStruct;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.*;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {TeacherServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TeacherServiceImplTest {
    @MockBean
    private DepartmentRepo departmentRepo;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherFileService teacherFileService;

    @MockBean
    private TeacherMapStruct teacherMapStruct;

    @MockBean
    private TeacherRepo teacherRepo;

    @Autowired
    private TeacherServiceImpl teacherServiceImpl;



    @Test
    void testCreateorupdatewithnullId() throws IOException{
        Teacher teacher=new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8098736321");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);
        HashSet<Department> departments=new HashSet<>();
        TeacherRequestDTO buildResult=TeacherRequestDTO.builder().department(departments).name("Name").phno("7025986547").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any())).thenReturn(buildResult);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);
        when(teacherFileService.upload(Mockito.<MultipartFile>any())).thenReturn(new TeacherProfileImg());
        when(teacherRepo.existsByPhno(Mockito.any(String.class))).thenReturn(false);
        when(teacherRepo.save(any(Teacher.class))).thenReturn(teacher);

        Responsedto responsedto=  teacherServiceImpl.createorupdate(null,"teacherData",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),teacher);
    }


    @Test
    void testCreateorupdateUpdate() throws IOException {
        // Arrange
        Teacher teacher=new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8098736321");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);

        HashSet<Department> departments=new HashSet<>();
        TeacherRequestDTO buildResult=TeacherRequestDTO.builder().department(departments).name("Name")
                .phno("7025986547").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any()))
                .thenReturn(buildResult);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);
        when(teacherFileService.upload(Mockito.<MultipartFile>any())).thenReturn(new TeacherProfileImg());
        when(teacherRepo.existsById(any(Long.class))).thenReturn(true);
        when(teacherRepo.existsByPhno(Mockito.any(String.class))).thenReturn(true);
        when(teacherRepo.findByPhno(any(String.class))).thenReturn(Optional.of(teacher));
        when(teacherRepo.findById(any(Long.class))).thenReturn(Optional.of(teacher));
        when(teacherFileService.deleteFile(any(String.class))).thenReturn(new Responsedto<>());
        when(teacherRepo.save(any(Teacher.class))).thenReturn(teacher);

        Responsedto responsedto=  teacherServiceImpl.createorupdate(1L,"teacherData",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX"
                        .getBytes("UTF-8"))));

        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),teacher);


    }

    @Test
    void testCreateorupdateUpdateWithNoFile() throws IOException {
        Teacher teacher=new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8098736321");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);

        HashSet<Department> departments=new HashSet<>();
        TeacherRequestDTO buildResult=TeacherRequestDTO.builder().department(departments).name("Name")
                .phno("7025986547").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any())).thenReturn(buildResult);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);
        when(teacherRepo.existsById(any(Long.class))).thenReturn(true);
        when(teacherRepo.findById(any(Long.class))).thenReturn(Optional.of(teacher));

        when(teacherRepo.existsByPhno(Mockito.any(String.class))).thenReturn(true);
        when(teacherRepo.findByPhno(any(String.class))).thenReturn(Optional.of(teacher));
        when(teacherRepo.save(any(Teacher.class))).thenReturn(teacher);

        Responsedto responsedto=  teacherServiceImpl.createorupdate(1L,"teacherData",
                null);
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),teacher);
    }

//    @Test
//    void testCreatewithDuplicatePhoneNumber() {
//        // Arrange
//        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
//
//        // Act and Assert
//        assertThrows(BadRequest.class, () -> teacherServiceImpl.createorupdate(1L,"teacherData",
//                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
//        verify(teacherRepo).existsByPhno(Mockito.<String>any());
//    }

    @Test
    void testFindAll() {
        // Arrange
        when(teacherRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Responsedto<List<Teacher>> actualFindAllResult = teacherServiceImpl.findAll(3, 10, "Sort");

        // Assert
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
        assertEquals("Teachers List", actualFindAllResult.getMessage());
        assertTrue(actualFindAllResult.getSuccess());
        assertTrue(actualFindAllResult.getResult().isEmpty());
    }


    @Test
    void testFindAll2() {
        // Arrange
        when(teacherRepo.findAll(Mockito.<Pageable>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.findAll(3, 10, "Sort"));
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
    }


    @Test
    void testViewDetails() {
        // Arrange
        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();
        teacherProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg.setFilePath("/directory/foo.txt");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("Name");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setTeacher(new Teacher());
        teacherProfileImg.setType("Type");

        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTeacherProfileImg(teacherProfileImg);
        teacher.setTid(1L);

        TeacherProfileImg teacherProfileImg2 = new TeacherProfileImg();
        teacherProfileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg2.setFilePath("/directory/foo.txt");
        teacherProfileImg2.setId(1L);
        teacherProfileImg2.setName("Name");
        teacherProfileImg2.setSize(3L);
        teacherProfileImg2.setTeacher(teacher);
        teacherProfileImg2.setType("Type");

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTeacherProfileImg(teacherProfileImg2);
        teacher2.setTid(1L);
        Optional<Teacher> ofResult = Optional.of(teacher2);
        when(teacherRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Responsedto<Teacher> actualViewDetailsResult = teacherServiceImpl.viewDetails(1L);

        // Assert
        verify(teacherRepo).findById(Mockito.<Long>any());
        assertEquals("Teacher Details", actualViewDetailsResult.getMessage());
        assertTrue(actualViewDetailsResult.getSuccess());
        assertSame(teacher2, actualViewDetailsResult.getResult());
    }

    @Test
    void testViewDetails2() {
        // Arrange
        Optional<Teacher> emptyResult = Optional.empty();
        when(teacherRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> teacherServiceImpl.viewDetails(1L));
        verify(teacherRepo).findById(Mockito.<Long>any());
    }


    @Test
    void testViewDetails3() {
        // Arrange
        when(teacherRepo.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.viewDetails(1L));
        verify(teacherRepo).findById(Mockito.<Long>any());
    }


    @Test
    void testDelete() {
        // Arrange
        doNothing().when(teacherRepo).deleteById(Mockito.<Long>any());

        // Act
        Responsedto actualDeleteResult = teacherServiceImpl.delete(1L);

        // Assert
        verify(teacherRepo).deleteById(Mockito.<Long>any());
        assertEquals("Deleted Successfully", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }


    @Test
    void testDelete2() {
        // Arrange
        doThrow(new RuntimeException("Deleted Successfully")).when(teacherRepo).deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.delete(1L));
        verify(teacherRepo).deleteById(Mockito.<Long>any());
    }
}
