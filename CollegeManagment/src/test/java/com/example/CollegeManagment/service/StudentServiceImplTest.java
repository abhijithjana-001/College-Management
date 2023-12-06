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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addStudent() {
        Studentdto studentdto = new Studentdto();
        studentdto.setName("John Doe");
        studentdto.setDepartment(new Department());

        Department department = new Department();
        department.setId(1L);
        department.setStudents(Collections.emptySet());

        when(departmentRepo.findById(department.getId())).thenReturn(Optional.of(department));
        when(studentRepo.save(any(Student.class))).thenAnswer(invocation -> {
            Student savedStudent = invocation.getArgument(0);
            Set<Student> students = department.getStudents();
            students.add(savedStudent);
            department.setStudents(students);
            return savedStudent;
        });

        Responsedto<Student> response = studentService.addStudent(studentdto);

        assertTrue(response.getSuccess());
        assertEquals("student added successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals("John Doe", response.getResult().getSname());
        assertEquals(department, response.getResult().getDepartment());

        verify(departmentRepo, times(1)).findById(department.getId());
        verify(studentRepo, times(1)).save(any(Student.class));
        verifyNoMoreInteractions(departmentRepo, studentRepo);
    }
//
//    @Test
//    void addStudentDepartmentNotFound() {
//        Studentdto studentdto = new Studentdto();
//        studentdto.setName("John Doe");
//        studentdto.setDepartment(new Department());
//
//        when(departmentRepo.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThrows(ItemNotFound.class, () -> studentService.addStudent(studentdto));
//
//        verify(departmentRepo, times(1)).findById(anyLong());
//        verifyNoMoreInteractions(departmentRepo, studentRepo);
//    }
//
//    @Test
//    void viewDetails() {
//        long studentId = 1L;
//        Student expectedStudent = new Student();
//        expectedStudent.setId(studentId);
//        expectedStudent.setSname("John Doe");
//
//        when(studentRepo.findById(studentId)).thenReturn(Optional.of(expectedStudent));
//
//        Responsedto<Student> response = studentService.viewdetails(studentId);
//
//        assertTrue(response.isSuccess());
//        assertEquals("student added successful", response.getMessage());
//        assertEquals(expectedStudent, response.getData());
//
//        verify(studentRepo, times(1)).findById(studentId);
//        verifyNoMoreInteractions(departmentRepo, studentRepo);
//    }
//
//    @Test
//    void deleteById() {
//        long studentId = 1L;
//        Student expectedStudent = new Student();
//        expectedStudent.setId(studentId);
//
//        when(studentRepo.findById(studentId)).thenReturn(Optional.of(expectedStudent));
//
//        Responsedto<Student> response = studentService.deletebyid(studentId);
//
//        assertTrue(response.isSuccess());
//        assertEquals("student delete successful", response.getMessage());
//
//        verify(studentRepo, times(1)).findById(studentId);
//        verify(studentRepo, times(1)).deleteById(studentId);
//        verifyNoMoreInteractions(departmentRepo, studentRepo);
//    }
//
//    @Test
//    void listStudent() {
//        List<Student> students = Collections.singletonList(new Student());
//        when(studentRepo.findAll()).thenReturn(students);
//
//        Responsedto<List<Student>> response = studentService.listStudent();
//
//        assertTrue(response.isSuccess());
//        assertEquals("student list : 1 students", response.getMessage());
//        assertEquals(students, response.getData());
//
//        verify(studentRepo, times(1)).findAll();
//        verifyNoMoreInteractions(departmentRepo, studentRepo);
//    }
//
//    @Test
//    void updateStudent() {
//        long studentId = 1L;
//        Studentdto studentdto = new Studentdto();
//        studentdto.setName("Updated Student");
//
//        Student existingStudent = new Student();
//        existingStudent.setId(studentId);
//        existingStudent.setSname("Old Student");
//
//        when(studentRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));
//        when(studentRepo.save(any(Student.class))).thenReturn(existingStudent);
//
//        Responsedto<Student> response = studentService.updateStudent(studentdto, studentId);
//
//        assertTrue(response.isSuccess());
//        assertEquals("student updated successful", response.getMessage());
//        assertEquals(existingStudent, response.getData());
//    }
}
