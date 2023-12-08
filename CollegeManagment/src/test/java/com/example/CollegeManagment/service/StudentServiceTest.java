package com.example.CollegeManagment.service;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddOrUpdateStudent_AddNewStudent() {
        // Arrange
        Studentdto studentdto = new Studentdto("John Doe", new Department(), "1234567890");

        when(studentRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(studentRepo.existsByPhoneNum(anyString())).thenReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(new Student());

        // Act
        Responsedto<Student> response = studentService.addorupdateStudent(studentdto, null);

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added or updated successful", response.getMessage());
        assertNotNull(response.getResult());

        // Verify that save method was called
        verify(studentRepo, times(1)).save(any(Student.class));
    }

    @Test
    public void testAddOrUpdateStudent_UpdateExistingStudent() {
        // Arrange
        Long studentId = 1L;
        Studentdto studentdto = new Studentdto("Updated John Doe", new Department(), "1234567890");

        Student existingStudent = new Student();
        existingStudent.setStudent_id(studentId);
        existingStudent.setSname("John Doe");

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepo.existsByPhoneNum(anyString())).thenReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(existingStudent);

        // Act
        Responsedto<Student> response = studentService.addorupdateStudent(studentdto, studentId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added or updated successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals("Updated John Doe", response.getResult().getSname());

        // Verify that findById and save methods were called
        verify(studentRepo, times(1)).findById(studentId);
        verify(studentRepo, times(1)).save(any(Student.class));
    }

    @Test
    public void testAddOrUpdateStudent_DuplicatePhoneNumber() {
        // Arrange
        Studentdto studentdto = new Studentdto("John Doe", new Department(), "1234567890");

        Student existingStudent = new Student();
        existingStudent.setStudent_id(1L);
        existingStudent.setPhoneNum("1234567890");

        when(studentRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(studentRepo.existsByPhoneNum(anyString())).thenReturn(true);
        when(studentRepo.findByPhoneNum(anyString())).thenReturn(Optional.of(existingStudent));

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> studentService.addorupdateStudent(studentdto, null));

        // Verify that findById and save methods were not called
        verify(studentRepo, never()).findById(anyLong());
        verify(studentRepo, never()).save(any(Student.class));
    }

    @Test
    public void testViewDetails_ExistingStudent() {
        // Arrange
        Long studentId = 1L;
        Student existingStudent = new Student();
        existingStudent.setStudent_id(studentId);
        existingStudent.setSname("John Doe");

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Act
        Responsedto<Student> response = studentService.viewdetails(studentId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals(studentId, response.getResult().getStudent_id());

        // Verify that findById method was called
        verify(studentRepo, times(1)).findById(studentId);
    }

    @Test
    public void testViewDetails_NonExistingStudent() {
        // Arrange
        Long studentId = 1L;

        when(studentRepo.findById(studentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> studentService.viewdetails(studentId));

        // Verify that findById method was called
        verify(studentRepo, times(1)).findById(studentId);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long studentId = 1L;

        // Act
        Responsedto response = studentService.deletebyid(studentId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student delete successful", response.getMessage());

        // Verify that deleteById method was called
        verify(studentRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void testListStudent() {
        // Arrange
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());

        when(studentRepo.findAll()).thenReturn(students);

        // Act
        Responsedto<List<Student>> response = studentService.listStudent();

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student list : 2 students", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals(2, response.getResult().size());

        // Verify that findAll method was called
        verify(studentRepo, times(1)).findAll();
    }
}
