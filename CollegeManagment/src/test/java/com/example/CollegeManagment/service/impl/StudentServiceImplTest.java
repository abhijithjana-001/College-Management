package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.StudentFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private StudentFileService studentFileService;

    @Mock
    private StudentMaptructConfig studentMaptructConfig;

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
   private  StudentServiceImpl studentServiceImpl;

    @Test
    void testAddOrUpdateWithIdNull() throws IOException {
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        Studentdto buildResult = Studentdto.builder().department(new Department()).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);

        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student);
        when(studentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(new StudentProfileImg());

        when(studentRepo.existsByPhoneNum(Mockito.any(String.class))).thenReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(student);
        Responsedto<Student> responsedto=  studentServiceImpl.addorupdateStudent("StudentDto",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAPTA".getBytes("UTF-8"))), null);

        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
    }


    @Test
    void testAddOrUpdateForUpdate() throws IOException {
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        Studentdto buildResult = Studentdto.builder().department(new Department()).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student);
        when(studentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(new StudentProfileImg());
        when(studentRepo.existsById(any(Long.class))).thenReturn(true);
        when(studentRepo.existsByPhoneNum(Mockito.any(String.class))).thenReturn(true);
        when(studentRepo.findByPhoneNum(any(String.class))).thenReturn(Optional.of(student));
        when(studentRepo.findById(any(Long.class))).thenReturn(Optional.of(student));

        when(studentRepo.save(any(Student.class))).thenReturn(student);

        Responsedto<Student> responsedto=  studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L);
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
    }


    @Test
    void testAddOrUpdateForUpdateWithNoFile() throws IOException {
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(5L);


        Studentdto buildResult = Studentdto.builder().department(new Department()).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student);
        when(studentRepo.existsById(any(Long.class))).thenReturn(true);
        when(studentRepo.findById(any(Long.class))).thenReturn(Optional.of(student));

        when(studentRepo.existsByPhoneNum(Mockito.any(String.class))).thenReturn(true);
        when(studentRepo.findByPhoneNum(any(String.class))).thenReturn(Optional.of(student));
        when(studentRepo.save(any(Student.class))).thenReturn(student);


        Responsedto<Student> responsedto=  studentServiceImpl.addorupdateStudent("Studentdtodata",null, 5L);
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
    }


    @Test
    void testCreateWithDuplicatePhoneNum() {
        // Arrange
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(BadRequest.class, () -> studentServiceImpl.create(new Student()));
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#create(Student)}
     */
    @Test
    void testCreate() {
        // Arrange

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("6625550144");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        when(studentRepo.existsByPhoneNum(any(String.class))).thenReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(student);

        // Act
        Student actualCreateResult = studentServiceImpl.create(student);

        // Assert
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).save(Mockito.<Student>any());
        assertSame(student, actualCreateResult);
    }

    @Test
    void testUpdateStudentWithNewPh() throws IOException {
//        arrange
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("6625550144");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        when(studentRepo.existsByPhoneNum(any(String.class))).thenReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(student);

//        act
        Student result=studentServiceImpl.updateStudent(student,new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))),1L);

//assert
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).save(Mockito.<Student>any());
        assertSame(student, result);
    }



    @Test
    void testViewDetails() {
        // Arrange

        Student student2 = new Student();
        student2.setDepartment(new Department());
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(new StudentProfileImg());
        student2.setSname("Sname");
        student2.setStudentId(1L);

        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(Optional.of(student2));

        // Act
        Responsedto<Student> actualViewDetailsResult = studentServiceImpl.viewdetails(1L);

        // Assert
        verify(studentRepo).findById(Mockito.<Long>any());
        assertEquals("student added successful", actualViewDetailsResult.getMessage());
        assertTrue(actualViewDetailsResult.getSuccess());
        assertSame(student2, actualViewDetailsResult.getResult());
    }

    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(studentRepo).deleteById(Mockito.<Long>any());

        // Act
        Responsedto actualDeleteByIdResult = studentServiceImpl.deletebyid(1L);

        // Assert
        verify(studentRepo).deleteById(Mockito.<Long>any());
        assertEquals("student delete successful", actualDeleteByIdResult.getMessage());
        assertNull(actualDeleteByIdResult.getResult());
        assertTrue(actualDeleteByIdResult.getSuccess());
    }




    @Test
    void testListStudent() {
        // Arrange

        Page page= new PageImpl(Arrays.asList(new Student(),new Student(),new Student()));
        when(studentRepo.findAll(Mockito.<Pageable>any())).thenReturn(page);

        // Act
        Responsedto<List<Student>> actualListStudentResult = studentServiceImpl.listStudent(3, 10, "Sortby");

        // Assert
        verify(studentRepo).findAll(Mockito.<Pageable>any());
        assertEquals("student list : 3 students", actualListStudentResult.getMessage());
        assertTrue(actualListStudentResult.getSuccess());
        assertEquals(3,actualListStudentResult.getResult().size());

    }


    @Test
    void testUpdateIdNotFound() throws JsonProcessingException {
//        arrange
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(5L);


        Studentdto buildResult = Studentdto.builder().department(new Department()).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student);
        when(studentRepo.existsById(any(Long.class))).thenReturn(false);

//        act and assert
        assertThrows(ItemNotFound.class,()->studentServiceImpl.addorupdateStudent("student",null,3L));

    }





}
