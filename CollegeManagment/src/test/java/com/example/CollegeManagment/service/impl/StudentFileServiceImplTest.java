package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.*;
import com.example.CollegeManagment.repository.StudentProfileRepo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {StudentFileServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StudentFileServiceImplTest {
    @Autowired
    private StudentFileServiceImpl studentFileServiceImpl;

    @MockBean
    private StudentProfileRepo studentProfileRepo;


    @Test
    void testUpload()  {

        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        StudentProfileImg expectedStudentProfileImg = StudentProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("${file.path}\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(studentProfileRepo.existsByName(file.getOriginalFilename())).thenReturn(false);
        when(studentProfileRepo.save(any(StudentProfileImg.class))).thenReturn(expectedStudentProfileImg);

        // Act
        StudentProfileImg result = studentFileServiceImpl.upload(file);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStudentProfileImg.getName(), result.getName());
        assertEquals(expectedStudentProfileImg.getType(), result.getType());
        assertEquals(expectedStudentProfileImg.getSize(), result.getSize());
        assertEquals(expectedStudentProfileImg.getFilePath(), result.getFilePath());


        verify(studentProfileRepo, times(1)).existsByName(file.getOriginalFilename());

    }




    @Test
    void testFindByName() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
        StudentProfileImg studentProfileImg = StudentProfileImg.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filePath("${file.path}\\"+file.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(Optional.of(studentProfileImg));
//       act
        ImageData test = studentFileServiceImpl.findByName("test");


        // Assert
        assert.e test.contenttype()
    }

    @Test
    void testDeletefile() {
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

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("6625550144");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(student);
        profileImg.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg);
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
        Optional<StudentProfileImg> ofResult = Optional.of(studentProfileImg);
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile("foo.txt"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }


    @Test
    void testDeletefile2() {
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

        Student student = new Student();
        student.setDepartment(new Department());
        student.setPhoneNum("6625550144");
        student.setProfileImg(new StudentProfileImg());
        student.setSname("Sname");
        student.setStudentId(1L);

        StudentProfileImg profileImg = new StudentProfileImg();
        profileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        profileImg.setFilePath("/directory/foo.txt");
        profileImg.setId(1L);
        profileImg.setLink("Link");
        profileImg.setName("Name");
        profileImg.setSize(3L);
        profileImg.setStudent(student);
        profileImg.setType("Type");

        Student student2 = new Student();
        student2.setDepartment(department);
        student2.setPhoneNum("6625550144");
        student2.setProfileImg(profileImg);
        student2.setSname("Sname");
        student2.setStudentId(1L);
        StudentProfileImg studentProfileImg = mock(StudentProfileImg.class);
        when(studentProfileImg.getFilePath()).thenReturn("/directory/foo.txt");
        doNothing().when(studentProfileImg).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(studentProfileImg).setFilePath(Mockito.<String>any());
        doNothing().when(studentProfileImg).setId(Mockito.<Long>any());
        doNothing().when(studentProfileImg).setLink(Mockito.<String>any());
        doNothing().when(studentProfileImg).setName(Mockito.<String>any());
        doNothing().when(studentProfileImg).setSize(Mockito.<Long>any());
        doNothing().when(studentProfileImg).setStudent(Mockito.<Student>any());
        doNothing().when(studentProfileImg).setType(Mockito.<String>any());
        studentProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        studentProfileImg.setFilePath("/directory/foo.txt");
        studentProfileImg.setId(1L);
        studentProfileImg.setLink("Link");
        studentProfileImg.setName("Name");
        studentProfileImg.setSize(3L);
        studentProfileImg.setStudent(student2);
        studentProfileImg.setType("Type");
        Optional<StudentProfileImg> ofResult = Optional.of(studentProfileImg);
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile("foo.txt"));
        verify(studentProfileImg).getFilePath();
        verify(studentProfileImg).setCreated(Mockito.<LocalDateTime>any());
        verify(studentProfileImg).setFilePath(Mockito.<String>any());
        verify(studentProfileImg).setId(Mockito.<Long>any());
        verify(studentProfileImg).setLink(Mockito.<String>any());
        verify(studentProfileImg).setName(Mockito.<String>any());
        verify(studentProfileImg).setSize(Mockito.<Long>any());
        verify(studentProfileImg).setStudent(Mockito.<Student>any());
        verify(studentProfileImg).setType(Mockito.<String>any());
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }
}
