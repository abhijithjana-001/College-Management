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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepo studentRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void testAddStudent() {
        Studentdto studentdto = new Studentdto("John Doe", new Department(1L, "Computer Science"));

        Department mockedDepartment = new Department(1L, "Computer Science");
        mockedDepartment.setStudents(new ArrayList<>());

        Student mockedStudent = new Student();
        mockedStudent.setSname("John Doe");
        mockedStudent.setDepartment(mockedDepartment);

        when(departmentRepo.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(studentRepo.save(any(Student.class))).thenReturn(mockedStudent);


        Responsedto<Student> response = studentService.addStudent(studentdto,1L);


        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals("John Doe", response.getResult().getSname());


        verify(studentRepo, times(1)).save(any(Student.class));
    }

    @Test
    public void testViewDetails() {

        Long studentId = 1L;

        Student mockedStudent = new Student();
        mockedStudent.setStudent_id(studentId);
        mockedStudent.setSname("John Doe");
        mockedStudent.setDepartment(new Department(1L, "Computer Science"));
        when(studentRepo.findById(anyLong())).thenReturn(Optional.of(mockedStudent));


        Responsedto<Student> response = studentService.viewdetails(studentId);


        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals(studentId, response.getResult().getStudent_id());


        verify(studentRepo, times(1)).findById(anyLong());
    }

    @Test
    public void testViewDetailsWithNonExistingStudent() {

        Long studentId = 1L;

        when(studentRepo.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(ItemNotFound.class, () -> studentService.viewdetails(studentId));


        verify(studentRepo, times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteById() {

        Long studentId = 1L;

        // Perform the test
        Responsedto response = studentService.deletebyid(studentId);


        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student delete successful", response.getMessage());


        verify(studentRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void testListStudent() {

        List<Student> mockedStudents = Arrays.asList(
                new Student(1L, "John Doe",new Department(1L, "Computer Science")),
                new Student(2L, "Jane Doe", new Department(2L, "Physics"))
        );

        when(studentRepo.findAll()).thenReturn(mockedStudents);


        Responsedto<List<Student>> response = studentService.listStudent();


        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student list : 2 students", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals(2, response.getResult().size());


        verify(studentRepo, times(1)).findAll();
    }

    @Test
    public void testUpdateStudent() {

        Long studentId = 1L;
        Studentdto updatedStudentDto = new Studentdto("Updated John Doe", new Department(1L, "Computer Science"));

        Student existingStudent = new Student();
        existingStudent.setStudent_id(studentId);
        existingStudent.setSname("John Doe");
        existingStudent.setDepartment(new Department(1L, "Computer Science"));

        when(studentRepo.findById(anyLong())).thenReturn(Optional.of(existingStudent));
        when(studentRepo.save(any(Student.class))).thenReturn(existingStudent);

        Responsedto<Student> response = studentService.updateStudent(updatedStudentDto, studentId);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student updated successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals("Updated John Doe", response.getResult().getSname());


        verify(studentRepo, times(1)).findById(studentId);
        verify(studentRepo, times(1)).save(any(Student.class));
    }


}