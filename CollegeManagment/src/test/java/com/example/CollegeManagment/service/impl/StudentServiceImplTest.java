package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
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
class StudentServiceImplTest {
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
        Studentdto buildResult = Studentdto.builder().department(department2).phoneNum("6625550144").sname("Sname").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any())).thenReturn(buildResult);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentServiceImpl.addorupdateStudent("Studentdtodata",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Studentdto>>any());
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
