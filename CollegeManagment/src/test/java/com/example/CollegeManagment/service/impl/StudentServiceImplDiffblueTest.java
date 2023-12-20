package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
class StudentServiceImplDiffblueTest {
    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private StudentFileServiceImpl studentFileServiceImpl;

    @MockBean
    private StudentMaptructConfig studentMaptructConfig;

    @MockBean
    private StudentProfileRepo studentProfileRepo;

    @MockBean
    private StudentRepo studentRepo;

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(String, MultipartFile, Long)}
     */
    @Test
    void testAddorupdateStudent() throws IOException {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        Studentdto buildResult = Studentdto.builder().department(department).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Department department3 = new Department();
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
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

        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        studentProfileImg.setFilePath("/directory/foo.txt");
        studentProfileImg.setId(1L);
        studentProfileImg.setName("Name");
        studentProfileImg.setSize(3L);
        studentProfileImg.setStudent(student2);
        studentProfileImg.setType("Type");
        when(studentFileServiceImpl.upload(Mockito.<MultipartFile>any())).thenReturn(studentProfileImg);

        Department department4 = new Department();
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        Department department5 = new Department();
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());

        StudentProfileImg profileImg3 = new StudentProfileImg();
        profileImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg3.setFilePath("/directory/foo.txt");
        profileImg3.setId(1L);
        profileImg3.setName("Name");
        profileImg3.setSize(3L);
        profileImg3.setStudent(new Student());
        profileImg3.setType("Type");

        Student student3 = new Student();
        student3.setDepartment(department5);
        student3.setPhoneNum("6625550144");
        student3.setProfileImg(profileImg3);
        student3.setSname("Sname");
        student3.setStudentId(1L);

        StudentProfileImg profileImg4 = new StudentProfileImg();
        profileImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg4.setFilePath("/directory/foo.txt");
        profileImg4.setId(1L);
        profileImg4.setName("Name");
        profileImg4.setSize(3L);
        profileImg4.setStudent(student3);
        profileImg4.setType("Type");

        Student student4 = new Student();
        student4.setDepartment(department4);
        student4.setPhoneNum("6625550144");
        student4.setProfileImg(profileImg4);
        student4.setSname("Sname");
        student4.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student4);
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Department department6 = new Department();
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        Department department7 = new Department();
        department7.setId(1L);
        department7.setName("Name");
        department7.setStudents(new HashSet<>());
        department7.setTeachers(new HashSet<>());

        Student student5 = new Student();
        student5.setDepartment(new Department());
        student5.setPhoneNum("6625550144");
        student5.setProfileImg(new StudentProfileImg());
        student5.setSname("Sname");
        student5.setStudentId(1L);

        StudentProfileImg profileImg5 = new StudentProfileImg();
        profileImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg5.setFilePath("/directory/foo.txt");
        profileImg5.setId(1L);
        profileImg5.setName("Name");
        profileImg5.setSize(3L);
        profileImg5.setStudent(student5);
        profileImg5.setType("Type");

        Student student6 = new Student();
        student6.setDepartment(department7);
        student6.setPhoneNum("6625550144");
        student6.setProfileImg(profileImg5);
        student6.setSname("Sname");
        student6.setStudentId(1L);

        StudentProfileImg profileImg6 = new StudentProfileImg();
        profileImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg6.setFilePath("/directory/foo.txt");
        profileImg6.setId(1L);
        profileImg6.setName("Name");
        profileImg6.setSize(3L);
        profileImg6.setStudent(student6);
        profileImg6.setType("Type");

        Student student7 = new Student();
        student7.setDepartment(department6);
        student7.setPhoneNum("6625550144");
        student7.setProfileImg(profileImg6);
        student7.setSname("Sname");
        student7.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student7);
        assertThrows(RuntimeException.class, () -> studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
        verify(studentFileServiceImpl).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any());
        verify(studentRepo, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(String, MultipartFile, Long)}
     */
    @Test
    void testAddorupdateStudent2() throws IOException {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        Studentdto buildResult = Studentdto.builder().department(department).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Department department3 = new Department();
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

        StudentProfileImg studentProfileImg = new StudentProfileImg();
        studentProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        studentProfileImg.setFilePath("/directory/foo.txt");
        studentProfileImg.setId(1L);
        studentProfileImg.setLink("Link");
        studentProfileImg.setName("Name");
        studentProfileImg.setSize(3L);
        studentProfileImg.setStudent(student2);
        studentProfileImg.setType("Type");
        when(studentFileServiceImpl.upload(Mockito.<MultipartFile>any())).thenReturn(studentProfileImg);

        Department department4 = new Department();
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        Department department5 = new Department();
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());

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
        student3.setDepartment(department5);
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
        student4.setDepartment(department4);
        student4.setPhoneNum("6625550144");
        student4.setProfileImg(profileImg4);
        student4.setSname("Sname");
        student4.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student4);
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Department department6 = new Department();
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        Department department7 = new Department();
        department7.setId(1L);
        department7.setName("Name");
        department7.setStudents(new HashSet<>());
        department7.setTeachers(new HashSet<>());

        Student student5 = new Student();
        student5.setDepartment(new Department());
        student5.setPhoneNum("6625550144");
        student5.setProfileImg(new StudentProfileImg());
        student5.setSname("Sname");
        student5.setStudentId(1L);

        StudentProfileImg profileImg5 = new StudentProfileImg();
        profileImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg5.setFilePath("/directory/foo.txt");
        profileImg5.setId(1L);
        profileImg5.setLink("Link");
        profileImg5.setName("Name");
        profileImg5.setSize(3L);
        profileImg5.setStudent(student5);
        profileImg5.setType("Type");

        Student student6 = new Student();
        student6.setDepartment(department7);
        student6.setPhoneNum("6625550144");
        student6.setProfileImg(profileImg5);
        student6.setSname("Sname");
        student6.setStudentId(1L);

        StudentProfileImg profileImg6 = new StudentProfileImg();
        profileImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg6.setFilePath("/directory/foo.txt");
        profileImg6.setId(1L);
        profileImg6.setLink("Link");
        profileImg6.setName("Name");
        profileImg6.setSize(3L);
        profileImg6.setStudent(student6);
        profileImg6.setType("Type");

        Student student7 = new Student();
        student7.setDepartment(department6);
        student7.setPhoneNum("6625550144");
        student7.setProfileImg(profileImg6);
        student7.setSname("Sname");
        student7.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student7);
        assertThrows(RuntimeException.class, () -> studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
        verify(studentFileServiceImpl).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any());
        verify(studentRepo, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#viewdetails(Long)}
     */
    @Test
    void testViewdetails() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(new Student());
        profileImg.setType("Type");

        Student student = new Student();
        student.setDepartment(department2);
        student.setPhoneNum("6625550144");
        student.setProfileImg(profileImg);
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg2 = new StudentProfileImg();
        profileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg2.setFilePath("/directory/foo.txt");
        profileImg2.setId(1L);
        profileImg2.setName("Name");
        profileImg2.setSize(3L);
        profileImg2.setStudent(student);
        profileImg2.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg2);
        student2.setSname("Sname");
        student2.setStudentId(1L);

        Optional<Student> ofResult = Optional.of(student2);
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Responsedto<Student> actualViewdetailsResult = studentServiceImpl.viewdetails(1L);
        verify(studentRepo).findById(Mockito.<Long>any());
        assertEquals("student added successful", actualViewdetailsResult.getMessage());
        assertTrue(actualViewdetailsResult.getSuccess());
        assertSame(student2, actualViewdetailsResult.getResult());
    }

    /**
     * Method under test: {@link StudentServiceImpl#viewdetails(Long)}
     */
    @Test

    void testViewdetails2() {
        Optional<Student> emptyResult = Optional.empty();
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> studentServiceImpl.viewdetails(1L));
        verify(studentRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#viewdetails(Long)}
     */
    @Test
    void testViewdetails3() {
        when(studentRepo.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> studentServiceImpl.viewdetails(1L));
        verify(studentRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link StudentServiceImpl#viewdetails(Long)}
     */
    @Test
    void testViewdetails4() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

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
        student.setDepartment(department2);
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
        student2.setDepartment(department);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg2);
        student2.setSname("Sname");
        student2.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student2);
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Responsedto<Student> actualViewdetailsResult = studentServiceImpl.viewdetails(1L);
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
        doNothing().when(studentRepo).deleteById(Mockito.<Long>any());
        Responsedto actualDeletebyidResult = studentServiceImpl.deletebyid(1L);
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
        doThrow(new RuntimeException("student delete successful")).when(studentRepo).deleteById(Mockito.<Long>any());
        assertThrows(RuntimeException.class, () -> studentServiceImpl.deletebyid(1L));
        verify(studentRepo).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#listStudent(Integer, Integer, String)}
     */
    @Test
    void testListStudent() {
        when(studentRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Responsedto<List<Student>> actualListStudentResult = studentServiceImpl.listStudent(3, 10, "Sortby");
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
        when(studentRepo.findAll(Mockito.<Pageable>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> studentServiceImpl.listStudent(3, 10, "Sortby"));
        verify(studentRepo).findAll(Mockito.<Pageable>any());
    }
}
