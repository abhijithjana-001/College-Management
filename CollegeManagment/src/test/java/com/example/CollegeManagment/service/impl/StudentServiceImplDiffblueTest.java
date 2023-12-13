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

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.StudentRepo;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StudentServiceImplDiffblueTest {
    @MockBean
    private StudentMaptructConfig studentMaptructConfig;

    @MockBean
    private StudentRepo studentRepo;

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(Studentdto, Long)}
     */
    @Test
    void testAddorupdateStudent() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(1L);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setSname("Sname");
        student2.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student2);
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenReturn(ofResult);
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);
        when(studentRepo.save(Mockito.<Student>any())).thenReturn(student);
        when(studentRepo.existsById(Mockito.<Long>any())).thenReturn(true);

        Department department3 = new Department();
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        Student student3 = new Student();
        student3.setDepartment(department3);
        student3.setPhoneNum("6625550144");
        student3.setSname("Sname");
        student3.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student3);
        Responsedto<Student> actualAddorupdateStudentResult = studentServiceImpl.addorupdateStudent(new Studentdto(), 1L);
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
        verify(studentRepo).existsById(Mockito.<Long>any());
        verify(studentRepo).save(Mockito.<Student>any());
        assertEquals("student added or updated successful", actualAddorupdateStudentResult.getMessage());
        Student result = actualAddorupdateStudentResult.getResult();
        assertEquals(1L, result.getStudentId().longValue());
        assertTrue(actualAddorupdateStudentResult.getSuccess());
        assertEquals(student2, result);
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(Studentdto, Long)}
     */
    @Test
    void testAddorupdateStudent2() {
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenThrow(new ItemNotFound("Msg"));
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);
        when(studentRepo.existsById(Mockito.<Long>any())).thenReturn(true);

        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student);
        assertThrows(ItemNotFound.class, () -> studentServiceImpl.addorupdateStudent(new Studentdto(), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
        verify(studentRepo).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(Studentdto, Long)}
     */
    @Test
    void testAddorupdateStudent3() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(2L);
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenReturn(ofResult);
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);
        when(studentRepo.existsById(Mockito.<Long>any())).thenReturn(true);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setSname("Sname");
        student2.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student2);
        assertThrows(BadRequest.class, () -> studentServiceImpl.addorupdateStudent(new Studentdto(), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
        verify(studentRepo).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(Studentdto, Long)}
     */
    @Test
    void testAddorupdateStudent4() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(1L);
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(false);
        when(studentRepo.save(Mockito.<Student>any())).thenReturn(student);
        when(studentRepo.existsById(Mockito.<Long>any())).thenReturn(true);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setSname("Sname");
        student2.setStudentId(1L);
        Optional.of(student2);

        Department department3 = new Department();
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());

        Student student3 = new Student();
        student3.setDepartment(department3);
        student3.setPhoneNum("6625550144");
        student3.setSname("Sname");
        student3.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student3);
        Responsedto<Student> actualAddorupdateStudentResult = studentServiceImpl.addorupdateStudent(new Studentdto(), 1L);
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).existsById(Mockito.<Long>any());
        verify(studentRepo).save(Mockito.<Student>any());
        assertEquals("student added or updated successful", actualAddorupdateStudentResult.getMessage());
        Student result = actualAddorupdateStudentResult.getResult();
        assertEquals(1L, result.getStudentId().longValue());
        assertTrue(actualAddorupdateStudentResult.getSuccess());
        assertEquals(student2, result);
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(Studentdto, Long)}
     */
    @Test
    void testAddorupdateStudent5() {
        when(studentRepo.existsById(Mockito.<Long>any())).thenReturn(false);

        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student);
        assertThrows(ItemNotFound.class, () -> studentServiceImpl.addorupdateStudent(new Studentdto(), 1L));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link StudentServiceImpl#addorupdateStudent(Studentdto, Long)}
     */
    @Test
    void testAddorupdateStudent6() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepo.findByPhoneNum(Mockito.<String>any())).thenReturn(ofResult);
        when(studentRepo.existsByPhoneNum(Mockito.<String>any())).thenReturn(true);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        Student student2 = new Student();
        student2.setDepartment(department2);
        student2.setPhoneNum("6625550144");
        student2.setSname("Sname");
        student2.setStudentId(1L);
        when(studentMaptructConfig.toEntity(Mockito.<Studentdto>any())).thenReturn(student2);
        assertThrows(BadRequest.class, () -> studentServiceImpl.addorupdateStudent(new Studentdto(), null));
        verify(studentMaptructConfig).toEntity(Mockito.<Studentdto>any());
        verify(studentRepo).existsByPhoneNum(Mockito.<String>any());
        verify(studentRepo).findByPhoneNum(Mockito.<String>any());
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

        Student student = new Student();
        student.setDepartment(department);
        student.setPhoneNum("6625550144");
        student.setSname("Sname");
        student.setStudentId(1L);
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Responsedto<Student> actualViewdetailsResult = studentServiceImpl.viewdetails(1L);
        verify(studentRepo).findById(Mockito.<Long>any());
        assertEquals("student added successful", actualViewdetailsResult.getMessage());
        assertTrue(actualViewdetailsResult.getSuccess());
        assertSame(student, actualViewdetailsResult.getResult());
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
        when(studentRepo.findById(Mockito.<Long>any())).thenThrow(new ItemNotFound("Msg"));
        assertThrows(ItemNotFound.class, () -> studentServiceImpl.viewdetails(1L));
        verify(studentRepo).findById(Mockito.<Long>any());
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
        doThrow(new ItemNotFound("student delete successful")).when(studentRepo).deleteById(Mockito.<Long>any());
        assertThrows(ItemNotFound.class, () -> studentServiceImpl.deletebyid(1L));
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
        when(studentRepo.findAll(Mockito.<Pageable>any())).thenThrow(new ItemNotFound("Msg"));
        assertThrows(ItemNotFound.class, () -> studentServiceImpl.listStudent(3, 10, "Sortby"));
        verify(studentRepo).findAll(Mockito.<Pageable>any());
    }
}
