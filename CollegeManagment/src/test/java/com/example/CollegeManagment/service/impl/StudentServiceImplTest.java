package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.StudentFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

@ContextConfiguration(classes = {StudentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StudentServiceImplTest {
    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private StudentFileService studentFileService;

    @MockBean
    private StudentMaptructConfig studentMaptructConfig;

    @MockBean
    private StudentProfileRepo studentProfileRepo;

    @MockBean
    private StudentRepo studentRepo;

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Test
    void testAddorupdatewithidnull() throws IOException {
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
        Responsedto responsedto=  studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), null);

        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
    }


    @Test
    void testAddorupdateforupdate() throws IOException {
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
        when(studentRepo.existsByPhoneNum(Mockito.any(String.class))).thenReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(student);

        Responsedto responsedto=  studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 5L);
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
    }


    @Test
    void testAddorupdateforupdatewithnofile() throws IOException {
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
        when(studentFileService.deletefile(any(String.class))).thenReturn(new Responsedto<>());

        Responsedto responsedto=  studentServiceImpl.addorupdateStudent("Studentdtodata",null, 5L);
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(String, MultipartFile, Long)}
     */
    @Test
    void testAddorupdateStudent() throws IOException {
        // Arrange
        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(new Department());
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");



        DepartmentFileEntity departmentImg2 = new DepartmentFileEntity();
        departmentImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg2.setDepartment(new Department());
        departmentImg2.setFilePath("/directory/foo.txt");
        departmentImg2.setId(1L);
        departmentImg2.setName("Name");
        departmentImg2.setSize(3L);
        departmentImg2.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg2);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg3 = new DepartmentFileEntity();
        departmentImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg3.setDepartment(new Department());
        departmentImg3.setFilePath("/directory/foo.txt");
        departmentImg3.setId(1L);
        departmentImg3.setName("Name");
        departmentImg3.setSize(3L);
        departmentImg3.setType("Type");

        Department department3 = new Department();
        department3.setDepartmentImg(departmentImg3);
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("6625550144");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(student);
        profileImg.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department3);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg);
        student2.setSname("Sname");
        student2.setStudentId(1L);

        StudentProfileImg profileImg2 = new StudentProfileImg();
        profileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg2.setFilePath("/directory/foo.txt");
        profileImg2.setId(1L);
        profileImg2.setLink("Link");
        profileImg2.setName("Name");
        profileImg2.setSize(3L);
        profileImg2.setStudent(student2);
        profileImg2.setType("Type");

        Student student3 = new Student();
        student3.setDepartment(department2);
        student3.setPhoneNum("6625550144");
        student3.setProfileImg(profileImg2);
        student3.setSname("Sname");
        student3.setStudentId(1L);

        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student3);
        when(studentFileService.upload(Mockito.<MultipartFile>any())).thenThrow(new RuntimeException("foo"));

        DepartmentFileEntity departmentImg4 = new DepartmentFileEntity();
        departmentImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg4.setDepartment(new Department());
        departmentImg4.setFilePath("/directory/foo.txt");
        departmentImg4.setId(1L);
        departmentImg4.setName("Name");
        departmentImg4.setSize(3L);
        departmentImg4.setType("Type");

        Department department4 = new Department();
        department4.setDepartmentImg(departmentImg4);
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg5 = new DepartmentFileEntity();
        departmentImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg5.setDepartment(department4);
        departmentImg5.setFilePath("/directory/foo.txt");
        departmentImg5.setId(1L);
        departmentImg5.setName("Name");
        departmentImg5.setSize(3L);
        departmentImg5.setType("Type");

        Department department5 = new Department();
        department5.setDepartmentImg(departmentImg5);
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());
        Studentdto buildResult = Studentdto.builder().department(department5).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentFileService).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(String, MultipartFile, Long)}
     */
    @Test
    void testAddorupdateStudent2() throws IOException {
        // Arrange
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#create(Student)}
     */
    @Test
    void testCreate() {
        // Arrange
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);

        Department department = new Department();
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(department);
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Department department3 = new Department();
        department3.setDepartmentImg(new DepartmentFileEntity());
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(new Student());
        profileImg.setType("Type");

        Student student = new Student();
        student.setDepartment(department3);
        student.setPhoneNum("6625550144");
        student.setProfileImg(profileImg);
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg2 = new StudentProfileImg();
        profileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg2.setFilePath("/directory/foo.txt");
        profileImg2.setId(1L);
        profileImg2.setLink("Link");
        profileImg2.setName("Name");
        profileImg2.setSize(3L);
        profileImg2.setStudent(student);
        profileImg2.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg2);
        student2.setSname("Sname");
        student2.setStudentId(1L);

        // Act and Assert
        assertThrows(BadRequest.class, () -> studentServiceImpl.create(student2));
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#create(Student)}
     */
    @Test
    void testCreate2() {
        // Arrange
        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(new Department());
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department = new Department();
        department.setDepartmentImg(departmentImg);
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg2 = new DepartmentFileEntity();
        departmentImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg2.setDepartment(department);
        departmentImg2.setFilePath("/directory/foo.txt");
        departmentImg2.setId(1L);
        departmentImg2.setName("Name");
        departmentImg2.setSize(3L);
        departmentImg2.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg2);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg3 = new DepartmentFileEntity();
        departmentImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg3.setDepartment(new Department());
        departmentImg3.setFilePath("/directory/foo.txt");
        departmentImg3.setId(1L);
        departmentImg3.setName("Name");
        departmentImg3.setSize(3L);
        departmentImg3.setType("Type");

        Department department3 = new Department();
        department3.setDepartmentImg(departmentImg3);
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("6625550144");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(student);
        profileImg.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department3);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg);
        student2.setSname("Sname");
        student2.setStudentId(1L);

        StudentProfileImg profileImg2 = new StudentProfileImg();
        profileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg2.setFilePath("/directory/foo.txt");
        profileImg2.setId(1L);
        profileImg2.setLink("Link");
        profileImg2.setName("Name");
        profileImg2.setSize(3L);
        profileImg2.setStudent(student2);
        profileImg2.setType("Type");

        Student student3 = new Student();
        student3.setDepartment(department2);
        student3.setPhoneNum("6625550144");
        student3.setProfileImg(profileImg2);
        student3.setSname("Sname");
        student3.setStudentId(1L);
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(false);
        when(studentRepo.save(Mockito.<Student>any())).thenReturn(student3);

        Department department4 = new Department();
        department4.setDepartmentImg(new DepartmentFileEntity());
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg4 = new DepartmentFileEntity();
        departmentImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg4.setDepartment(department4);
        departmentImg4.setFilePath("/directory/foo.txt");
        departmentImg4.setId(1L);
        departmentImg4.setName("Name");
        departmentImg4.setSize(3L);
        departmentImg4.setType("Type");

        Department department5 = new Department();
        department5.setDepartmentImg(departmentImg4);
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());

        Department department6 = new Department();
        department6.setDepartmentImg(new DepartmentFileEntity());
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        StudentProfileImg profileImg3 = new StudentProfileImg();
        profileImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg3.setFilePath("/directory/foo.txt");
        profileImg3.setId(1L);
        profileImg3.setLink("Link");
        profileImg3.setName("Name");
        profileImg3.setSize(3L);
        profileImg3.setStudent(new Student());
        profileImg3.setType("Type");

        Student student4 = new Student();
        student4.setDepartment(department6);
        student4.setPhoneNum("6625550144");
        student4.setProfileImg(profileImg3);
        student4.setSname("Sname");
        student4.setStudentId(1L);

        StudentProfileImg profileImg4 = new StudentProfileImg();
        profileImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg4.setFilePath("/directory/foo.txt");
        profileImg4.setId(1L);
        profileImg4.setLink("Link");
        profileImg4.setName("Name");
        profileImg4.setSize(3L);
        profileImg4.setStudent(student4);
        profileImg4.setType("Type");

        Student student5 = new Student();
        student5.setDepartment(department5);
        student5.setPhoneNum("6625550144");
        student5.setProfileImg(profileImg4);
        student5.setSname("Sname");
        student5.setStudentId(1L);

        // Act
        Student actualCreateResult = studentServiceImpl.create(student5);

        // Assert
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).save(Mockito.<Student>any());
        assertSame(student3, actualCreateResult);
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#updateStudent(Student, MultipartFile, Long)}
     */
    @Test
    void testUpdateStudent() throws IOException {
        // Arrange
        Department department = new Department();
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(department);
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Department department3 = new Department();
        department3.setDepartmentImg(new DepartmentFileEntity());
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(new Student());
        profileImg.setType("Type");

        Student student = new Student();
        student.setDepartment(department3);
        student.setPhoneNum("6625550144");
        student.setProfileImg(profileImg);
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg2 = new StudentProfileImg();
        profileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg2.setFilePath("/directory/foo.txt");
        profileImg2.setId(1L);
        profileImg2.setLink("Link");
        profileImg2.setName("Name");
        profileImg2.setSize(3L);
        profileImg2.setStudent(student);
        profileImg2.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg2);
        student2.setSname("Sname");
        student2.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student2);
        when(studentRepo.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenReturn(ofResult);
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);

        Department department4 = new Department();
        department4.setDepartmentImg(new DepartmentFileEntity());
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg2 = new DepartmentFileEntity();
        departmentImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg2.setDepartment(department4);
        departmentImg2.setFilePath("/directory/foo.txt");
        departmentImg2.setId(1L);
        departmentImg2.setName("Name");
        departmentImg2.setSize(3L);
        departmentImg2.setType("Type");

        Department department5 = new Department();
        department5.setDepartmentImg(departmentImg2);
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());

        Department department6 = new Department();
        department6.setDepartmentImg(new DepartmentFileEntity());
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        StudentProfileImg profileImg3 = new StudentProfileImg();
        profileImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg3.setFilePath("/directory/foo.txt");
        profileImg3.setId(1L);
        profileImg3.setLink("Link");
        profileImg3.setName("Name");
        profileImg3.setSize(3L);
        profileImg3.setStudent(new Student());
        profileImg3.setType("Type");

        Student student3 = new Student();
        student3.setDepartment(department6);
        student3.setPhoneNum("6625550144");
        student3.setProfileImg(profileImg3);
        student3.setSname("Sname");
        student3.setStudentId(1L);

        StudentProfileImg profileImg4 = new StudentProfileImg();
        profileImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg4.setFilePath("/directory/foo.txt");
        profileImg4.setId(1L);
        profileImg4.setLink("Link");
        profileImg4.setName("Name");
        profileImg4.setSize(3L);
        profileImg4.setStudent(student3);
        profileImg4.setType("Type");

        Student student4 = new Student();
        student4.setDepartment(department5);
        student4.setPhoneNum("6625550144");
        student4.setProfileImg(profileImg4);
        student4.setSname("Sname");
        student4.setStudentId(1L);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentServiceImpl.updateStudent(student4,
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#viewdetails(Long)}
     */
    @Test
    void testViewdetails() {
        // Arrange
        Department department = new Department();
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(department);
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Department department3 = new Department();
        department3.setDepartmentImg(new DepartmentFileEntity());
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(new Student());
        profileImg.setType("Type");

        Student student = new Student();
        student.setDepartment(department3);
        student.setPhoneNum("6625550144");
        student.setProfileImg(profileImg);
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg2 = new StudentProfileImg();
        profileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg2.setFilePath("/directory/foo.txt");
        profileImg2.setId(1L);
        profileImg2.setLink("Link");
        profileImg2.setName("Name");
        profileImg2.setSize(3L);
        profileImg2.setStudent(student);
        profileImg2.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg2);
        student2.setSname("Sname");
        student2.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student2);
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Responsedto<Student> actualViewdetailsResult = studentServiceImpl.viewdetails(1L);

        // Assert
        verify(studentRepo).findById(Mockito.<Long>any());
        assertEquals("student added successful", actualViewdetailsResult.getMessage());
        assertTrue(actualViewdetailsResult.getSuccess());
        assertSame(student2, actualViewdetailsResult.getResult());
    }

    /**
     * Method under test: {@link StudentServiceImpl#deletebyid(Long)}
     */
    @Test
    void testDeletebyid() {
        // Arrange
        doNothing().when(studentRepo).deleteById(Mockito.<Long>any());

        // Act
        Responsedto actualDeletebyidResult = studentServiceImpl.deletebyid(1L);

        // Assert
        verify(studentRepo).deleteById(Mockito.<Long>any());
        assertEquals("student delete successful", actualDeletebyidResult.getMessage());
        assertNull(actualDeletebyidResult.getResult());
        assertTrue(actualDeletebyidResult.getSuccess());
    }

    /**
     * Method under test: {@link StudentServiceImpl#deletebyid(Long)}
     */
    @Test
    void testDeletebyid2() {
        // Arrange
        doThrow(new RuntimeException("student delete successful")).when(studentRepo).deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentServiceImpl.deletebyid(1L));
        verify(studentRepo).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#listStudent(Integer, Integer, String)}
     */
    @Test
    void testListStudent() {
        // Arrange
        when(studentRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Responsedto<List<Student>> actualListStudentResult = studentServiceImpl.listStudent(3, 10, "Sortby");

        // Assert
        verify(studentRepo).findAll(Mockito.<Pageable>any());
        assertEquals("student list : 0 students", actualListStudentResult.getMessage());
        assertTrue(actualListStudentResult.getSuccess());
        assertTrue(actualListStudentResult.getResult().isEmpty());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#listStudent(Integer, Integer, String)}
     */
    @Test
    void testListStudent2() {
        // Arrange
        when(studentRepo.findAll(Mockito.<Pageable>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentServiceImpl.listStudent(3, 10, "Sortby"));
        verify(studentRepo).findAll(Mockito.<Pageable>any());
    }
}
