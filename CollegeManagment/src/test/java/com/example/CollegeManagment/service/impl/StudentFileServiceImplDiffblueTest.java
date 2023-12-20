package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.repository.StudentProfileRepo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {StudentFileServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StudentFileServiceImplDiffblueTest {
    @Autowired
    private StudentFileServiceImpl studentFileServiceImpl;

    @MockBean
    private StudentProfileRepo studentProfileRepo;

    /**
     * Method under test: {@link StudentFileServiceImpl#upload(MultipartFile)}
     */
    @Test
    void testUpload() throws IOException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        StudentFileServiceImpl studentFileServiceImpl = new StudentFileServiceImpl(mock(StudentProfileRepo.class));
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.upload(new MockMultipartFile("image", "foo.txt",
                "text/plain", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#findByName(String)}
     */
    @Test
    void testFindByName() throws IOException {
        Optional<StudentProfileImg> emptyResult = Optional.empty();
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> studentFileServiceImpl.findByName("Name"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#findByName(String)}
     */
    @Test
    void testFindByName2() throws IOException {
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenThrow(new BadRequest("Msg"));
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.findByName("Name"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#deletefile(String)}
     */
    @Test
    void testDeletefile() {
        Department department = new Department();
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
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile("foo.txt"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#deletefile(String)}
     */
    @Test
    void testDeletefile2() {
        Optional<StudentProfileImg> emptyResult = Optional.empty();
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> studentFileServiceImpl.deletefile("foo.txt"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#deletefile(String)}
     */
    @Test
    void testDeletefile3() {
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenThrow(new BadRequest("Msg"));
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.deletefile("foo.txt"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }
}
