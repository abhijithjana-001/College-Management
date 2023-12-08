package com.example.CollegeManagment.service;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        Studentdto studentdto = new Studentdto("John Doe", new Department(1L, "Computer Science"), 1234567890L);

        Department mockedDepartment = new Department(1L, "Computer Science");
        mockedDepartment.setStudents(new HashSet<>());

        Student mockedStudent = new Student();
        mockedStudent.setSname("John Doe");
        mockedStudent.setDepartment(mockedDepartment);
        mockedStudent.setPhoneNum(1234567890L);

        when(studentRepo.save(any(Student.class))).thenReturn(mockedStudent);

        Responsedto<Student> response = studentService.addorupdateStudent(studentdto, null);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added or updated successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals("John Doe", response.getResult().getSname());



//     update
        Long studentId = 1L;
        Studentdto updatedStudentDto = new Studentdto("Updated John Doe", new Department(1L, "Computer Science"), 1234567890L);

        Student existingStudent = new Student();
        existingStudent.setStudent_id(studentId);
        existingStudent.setSname("John Doe");
        existingStudent.setDepartment(new Department(1L, "Computer Science"));
        existingStudent.setPhoneNum(123456785590L);

        when(studentRepo.findById(anyLong())).thenReturn(Optional.of(existingStudent));
        when(studentRepo.save(any(Student.class))).thenReturn(existingStudent);

        response = studentService.addorupdateStudent(updatedStudentDto, studentId);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals("student added or updated successful", response.getMessage());
        assertNotNull(response.getResult());
        assertEquals("Updated John Doe", response.getResult().getSname());

        verify(studentRepo, times(1)).findById(studentId);
        verify(studentRepo, times(2)).save(any(Student.class));
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
                new Student(1L, "John Doe", 1234567890L, new Department(1L, "Computer Science")),
                new Student(2L, "Jane Doe", 1234567890L, new Department(2L, "Physics"))
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
    public void exceptiontesting() {
        Studentdto studentdto =
                new Studentdto("John Doe", new Department(),1234567890L);
        Long studentId = 1L;
      Student student=new Student();
      student.setStudent_id(2L);
      student.setPhoneNum(231231231L);

        when(studentRepo.findByPhoneNum(studentdto.getPhoneNum()))
                .thenReturn(Optional.of(student));

        when(studentRepo.existsByPhoneNum(studentdto.getPhoneNum()))
                .thenReturn(true);

        // Act and Assert
        ItemNotFound exception = assertThrows(ItemNotFound.class,
                () -> studentService.addorupdateStudent(studentdto, studentId));
        ItemNotFound exception1 = assertThrows(ItemNotFound.class,
                () -> studentService.addorupdateStudent(studentdto,null));

        assertEquals("phone number already exist", exception1.getMessage());

        // Verify that save method is not called
        verify(studentRepo, never()).save(any());
//
    }
}