package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.TeacherMapStruct;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.*;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherFileRepository;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeacherServiceImplTest {

    @SpyBean
    private TeacherRepo teacherRepo;

    @SpyBean
    private TeacherServiceImpl teacherServiceImpl;

    @SpyBean
    private  TeacherFileService teacherFileService;
    @SpyBean
    private TeacherFileRepository teacherFileRepository;


    @Test
    void testCreateOrUpdateWithNullId() throws IOException{
        Teacher teacher=new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8098736321");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);
        doReturn(teacher).when(teacherRepo).save(Mockito.any(Teacher.class));
        doReturn(false).when(teacherFileRepository).existsByName(Mockito.any(String.class));
        doReturn(false).when(teacherRepo).existsByPhno(Mockito.any(String.class));

        Responsedto<Teacher> responsedto=  teacherServiceImpl.createorupdate(null,"{\"name\":\"Deepak\"," +
                        "\"phno\":\"8098736321\",\"department\":[{\"id\":1,\"name\":\"cse\"}]}",
                new MockMultipartFile( "Name",
                        "filename.txt",
                        "image/plain",
                        "AXAPTA".getBytes(StandardCharsets.UTF_8)
                ));

        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),teacher);
    }


    @Test
    void testCreateOrUpdate_Update() throws IOException {
        Teacher teacher=new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8098736321");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);

        doNothing().when(teacherFileService).deleteFile(anyString());
        doReturn(false).when(teacherFileRepository).existsByName(Mockito.any(String.class));
        doReturn(true).when(teacherRepo).existsById(any(Long.class));
        doReturn(Optional.of(teacher)).when(teacherRepo).findById(any(Long.class));
        doReturn(false).when(teacherRepo).existsByPhno(Mockito.any(String.class));
        doReturn(teacher).when(teacherRepo).save(any(Teacher.class));
        doReturn(Optional.of(teacher)).when(teacherRepo).findByPhno(any(String.class));


        Responsedto <Teacher> responsedto=  teacherServiceImpl.createorupdate(1L,"{\"name\":\"Deepak\"," +
                        "\"phno\":\"8098736321\",\"department\":[{\"id\":1,\"name\":\"cse\"}]}",
                new MockMultipartFile( "Name",
                        "filename.txt",
                        "image/plain",
                        "AXAPTA".getBytes(StandardCharsets.UTF_8)
                ));
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult(),teacher);
//        verify(teacherFileService,times(1)).deleteFile(anyString());

    }

    @Test
    void testCreateOrUpdate_UpdateWithNoFile() throws IOException {

        Teacher teacher=new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8098736321");
        TeacherProfileImg teacherProfileImg=new TeacherProfileImg();
        teacherProfileImg.setName("Image");
        teacher.setTid(1L);

        doReturn(true).when(teacherRepo).existsById(any(Long.class));
        doReturn(true).when(teacherRepo).existsByPhno(Mockito.any(String.class));
        doReturn(Optional.of(teacher)).when(teacherRepo).findById(any(Long.class));
        doReturn(Optional.of(teacher)).when(teacherRepo).findByPhno(any(String.class));
        doReturn(teacher).when(teacherRepo).save(any(Teacher.class));

        Responsedto <Teacher> responsedto=  teacherServiceImpl.createorupdate(1L,"{\"name\":\"Deepak\"," +
                "\"phno\":\"8098736321\",\"department\":[{\"id\":1,\"name\":\"cse\"}]}",null);
        assertEquals(responsedto.getSuccess(),true);
        assertEquals(responsedto.getResult().getName(),teacher.getName());
    }



    @Test
    void testFindAll() {
        // Arrange
        doReturn(new PageImpl<>(new ArrayList<>())).when(teacherRepo).findAll(Mockito.<Pageable>any());

        // Act
        Responsedto<List<Teacher>> actualFindAllResult = teacherServiceImpl.findAll(3, 10, "Sort");

        // Assert
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
        assertEquals("Teachers List", actualFindAllResult.getMessage());
        assertTrue(actualFindAllResult.getSuccess());
        assertTrue(actualFindAllResult.getResult().isEmpty());
    }


    @Test
    void testViewDetails() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Deepak");
        teacher.setPhno("8074566987");
        teacher.setTeacherProfileImg(new TeacherProfileImg());
        teacher.setTid(1L);


        doReturn(Optional.of(teacher)).when(teacherRepo).findById(any(Long.class));

        // Act
        Responsedto<Teacher> actualViewDetailsResult = teacherServiceImpl.viewDetails(1L);

        // Assert
        verify(teacherRepo).findById(Mockito.<Long>any());
        assertEquals("Teacher Details", actualViewDetailsResult.getMessage());
        assertTrue(actualViewDetailsResult.getSuccess());
        assertSame(teacher,actualViewDetailsResult.getResult());
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(teacherRepo).deleteById(Mockito.<Long>any());

        // Act
        Responsedto actualDeleteResult = teacherServiceImpl.delete(1L);

        // Assert
        verify(teacherRepo).deleteById(Mockito.<Long>any());
        assertEquals("Deleted Successfully", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }

}
