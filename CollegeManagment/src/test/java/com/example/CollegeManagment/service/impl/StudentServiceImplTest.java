package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.StudentMaptructConfig;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;
import com.example.CollegeManagment.repository.StudentRepo;
import com.example.CollegeManagment.service.StudentFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceImplTest {

    @SpyBean
    private StudentServiceImpl studentServiceImpl;

    @SpyBean
    private StudentRepo studentRepo;
    @SpyBean
    private StudentProfileRepo studentProfileRepo;

    @SpyBean
    private StudentFileService studentFileService;


    @Test
    void testAddOrUpdateWithIdNull()  {
//arrange
        StudentProfileImg studentProfileImg=new StudentProfileImg();
        studentProfileImg.setName("img");
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Abhijith Jana");
        student.setStudentId(1L);


        doReturn(student).when(studentRepo).save(Mockito.any(Student.class));
        doReturn(studentProfileImg).when(studentFileService).upload(Mockito.any(MultipartFile.class));
        doReturn(false).when(studentRepo).existsByPhoneNum(Mockito.any(String.class));
//      act
        Responsedto<Student> responsedto = studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",
                new MockMultipartFile( "Name",
                        "filename.txt",
                        "image/plain",
                        "AXAPTA".getBytes(StandardCharsets.UTF_8)
                ), null);
//assert

        assertEquals(responsedto.getSuccess(), true);
        assertSame(responsedto.getResult(),student);

    }


    @Test
    void testAddOrUpdateForUpdateWithFile()  {
//        arrange
        StudentProfileImg studentProfileImg=new StudentProfileImg();
        studentProfileImg.setName("img");

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(studentProfileImg);
        student.setSname("Abhijith jana");
        student.setStudentId(1L);

        doReturn(studentProfileImg).when(studentFileService).upload(Mockito.any(MultipartFile.class));
        doReturn(true).when(studentRepo)
                .existsById(any(Long.class));
        doReturn(Optional.of(student)).when(studentRepo)
                        .findById(any(Long.class));
        doReturn(false).when(studentRepo)
                .existsByPhoneNum(any(String.class));
        doReturn(student).when(studentRepo)
                .save(any(Student.class));
        doNothing().when(studentFileService).deletefile(any(String.class));

//      act
        Responsedto<Student> responsedto = studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",
                new MockMultipartFile( "Name",
                        "filename.txt",
                        "image/plain",
                        "AXAPTA".getBytes(StandardCharsets.UTF_8)
                ), 1L);
//        assert

        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);
        verify(studentFileService,times(1)).deletefile(anyString());
    }

    @Test
    void testAddOrUpdateForUpdateWithNoFile() {
//      arrange
        StudentProfileImg studentProfileImg=new StudentProfileImg();
        studentProfileImg.setName("img1");
//        studentProfileImg.setFilePath("C/:/Users/user453/Desktop/CollegeManagement/files/test/");

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(studentProfileImg);
        student.setSname("Abhijith Jana");
        student.setStudentId(1L);



        doReturn(true).when(studentRepo)
                .existsById(any(Long.class));
        doReturn(false).when(studentRepo)
                .existsByPhoneNum(any(String.class));
        doReturn(Optional.of(student)).when(studentRepo)
                .findById(any(Long.class));
        doReturn(Optional.of(student)).when(studentRepo)
                .findByPhoneNum(any(String.class));
        doReturn(student).when(studentRepo)
                .save(any(Student.class));



//       act
        Responsedto<Student> responsedto = studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",null, 1L);

//        assert
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);

    }

    @Test
    void testAddOrUpdateForUpdateWithSamePhoneNum() {
//      arrange
        StudentProfileImg studentProfileImg=new StudentProfileImg();
        studentProfileImg.setName("img1");
//        studentProfileImg.setFilePath("C/:/Users/user453/Desktop/CollegeManagement/files/test/");

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(studentProfileImg);
        student.setSname("Abhijith Jana");
        student.setStudentId(1L);



        doReturn(true).when(studentRepo)
                .existsById(any(Long.class));
        doReturn(Optional.of(student)).when(studentRepo)
                .findById(any(Long.class));
        doReturn(true).when(studentRepo)
                .existsByPhoneNum(any(String.class));
        doReturn(Optional.of(student)).when(studentRepo)
                .findById(any(Long.class));
        doReturn(Optional.of(student)).when(studentRepo)
                .findByPhoneNum(any(String.class));
        doReturn(student).when(studentRepo)
                .save(any(Student.class));



//       act
        Responsedto<Student> responsedto = studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",null, 1L);

//        assert
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),student);

    }


    @Test
    void testViewDetails() {
        // Arrange
        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("1234567890");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Abhijith Jana");
        student.setStudentId(1L);

        doReturn(Optional.of(student)).when(studentRepo).findById(any(Long.class));

        // Act
        Responsedto<Student> actualViewDetailsResult = studentServiceImpl.viewdetails(1L);

        // Assert
        verify(studentRepo).findById(Mockito.<Long>any());
        assertEquals("Abhijith Jana details:", actualViewDetailsResult.getMessage());
        assertTrue(actualViewDetailsResult.getSuccess());
        assertSame(student, actualViewDetailsResult.getResult());
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
        doReturn(page).when(studentRepo).findAll(any(Pageable.class));

        // Act
        Responsedto<List<Student>> actualListStudentResult = studentServiceImpl.listStudent(3, 10, "name");

        // Assert
        verify(studentRepo).findAll(Mockito.<Pageable>any());
        assertEquals("student list : 3 students", actualListStudentResult.getMessage());
        assertTrue(actualListStudentResult.getSuccess());
        assertEquals(3,actualListStudentResult.getResult().size());

    }

//Exception
    @Test
    void addOrUpdateStudentIdNotExist(){
//        arrange
        doReturn(false).when(studentRepo).existsById(any(Long.class));

//      act and assert
        assertThrows(ItemNotFound.class,()->
                studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",null, 1L)
                );

    }

    @Test
    void addOrUpdateStudentDuplicatePhoneNumber(){
//        arrange
        StudentProfileImg studentProfileImg=new StudentProfileImg();
        studentProfileImg.setName("img");

        doReturn(studentProfileImg).when(studentFileService).upload(Mockito.any(MultipartFile.class));

        doReturn(true).when(studentRepo).existsByPhoneNum(any(String.class));

//        act and assert
        assertThrows(BadRequest.class,()->
                studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",new MockMultipartFile(
                     "Name",
                                "filename.txt",
                                "image/plain",
                                "AXAPTA".getBytes(StandardCharsets.UTF_8)
                ), null)
        );
    }

    @Test
    void addOrUpdateStudentCreateWithNoImage(){
//        arrange
        doReturn(true).when(studentRepo).existsByPhoneNum(any(String.class));

//        act and assert
        assertThrows(BadRequest.class,()->
                studentServiceImpl.addorupdateStudent("{\"sname\":\"Abhijith Jana\",\"department\":{\"id\":1,\"name\":\"cse\"},\"phoneNum\":\"1234567890\"}",null, null)
        );
    }



}
