package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.Studentservice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentControllerTest {

    @SpyBean
    private Studentservice studentservice;

    @SpyBean
    private  StudentController studentController;

    @SpyBean
    StudentRepo studentRepo;

    @Test
    void testAddStudent() throws IOException {
        Responsedto<Student> responsedto=new Responsedto<>(true,"student added or updated successful",new Student());

        doReturn(responsedto).when(studentservice).addorupdateStudent(anyString(),any(MultipartFile.class),isNull());

        // Act
        ResponseEntity<Responsedto<Student>> responsedtoResponseEntity = studentController.addStudent( "studentDto", new MockMultipartFile("Name", new ByteArrayInputStream("AXAPTA".getBytes(StandardCharsets.UTF_8))));

        //        assert
        assertEquals(HttpStatusCode.valueOf(200),responsedtoResponseEntity.getStatusCode());
        assertEquals("student added or updated successful", Objects.requireNonNull(responsedtoResponseEntity.getBody()).getMessage());
        assertTrue(responsedtoResponseEntity.getBody().getSuccess());

    }

    @Test
    void testListStudent() {
        // Arrange
        Page<Student> page=new PageImpl<>(Arrays.asList(new Student(),new Student()));
        doReturn(page).when(studentRepo).findAll(any(Pageable.class));


        // Act
        ResponseEntity<Responsedto<List<Student>>> listStudent = studentController.listStudent(1,2,"sname");

        // Assert

        assertEquals(HttpStatusCode.valueOf(200), listStudent.getStatusCode());
        assertTrue(listStudent.hasBody());
        assertEquals(2, Objects.requireNonNull(listStudent.getBody()).getResult().size());

    }

@Test
void testDeleteStudent() {
    // Arrange
    Responsedto<Void> responsedto= new Responsedto<>(true,"student delete successful",null);
    doReturn(responsedto).when(studentservice).deletebyid(anyLong());
    // Act
    ResponseEntity<Responsedto<Void>> responsedtoResponseEntity = studentController.deleteStudent(7L);

    assertEquals(HttpStatusCode.valueOf(200), responsedtoResponseEntity.getStatusCode());
    assertTrue(responsedtoResponseEntity.hasBody());

}
    @Test
    void testFindStudent()  {
        // Arrange

        doReturn(Optional.of(new Student())).when(studentRepo).findById(anyLong());
        // Act
        ResponseEntity<Responsedto<Student>> findStudent = studentController.findstudent(1L);
//       assert
        assertEquals(HttpStatusCode.valueOf(200), findStudent.getStatusCode());
        assertTrue(findStudent.hasBody());

    }

    @Test
    void testUpdateStudent() throws Exception {
        // Arrange
        Responsedto<Student> responsedto=new Responsedto<>(true,"student added or updated successful",new Student());

       doReturn(responsedto).when(studentservice).addorupdateStudent(anyString(),any(MultipartFile.class),anyLong());

        // Act
        ResponseEntity<Responsedto<Student>> responsedtoResponseEntity = studentController.updateStudent(1L, "studentDto", new MockMultipartFile("Name", new ByteArrayInputStream("AXAPTA".getBytes(StandardCharsets.UTF_8))));

        //        assert
        assertEquals(HttpStatusCode.valueOf(200),responsedtoResponseEntity.getStatusCode());
        assertEquals("student added or updated successful", Objects.requireNonNull(responsedtoResponseEntity.getBody()).getMessage());
        assertTrue(responsedtoResponseEntity.getBody().getSuccess());

    }
}
