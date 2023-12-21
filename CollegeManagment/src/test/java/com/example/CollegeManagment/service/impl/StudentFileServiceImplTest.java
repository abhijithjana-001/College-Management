package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.entity.StudentProfileImg;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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

    /**
     * Method under test: {@link StudentFileServiceImpl#upload(MultipartFile)}
     */
    @Test
    void testUpload() throws IOException {

        // Arrange
        StudentFileServiceImpl studentFileServiceImpl = new StudentFileServiceImpl(mock(StudentProfileRepo.class));

        // Act and Assert
        assertThrows(BadRequest.class, () -> studentFileServiceImpl.upload(new MockMultipartFile("image", "foo.txt",
                "text/plain", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#findByName(String)}
     */
    @Test
    void testFindByName() throws IOException {


        // Arrange
        StudentProfileRepo studentProfileRepo = mock(StudentProfileRepo.class);
        Optional<StudentProfileImg> emptyResult = Optional.empty();
        when(studentProfileRepo.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> (new StudentFileServiceImpl(studentProfileRepo)).findByName("Name"));
        verify(studentProfileRepo).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentFileServiceImpl#deletefile(String)}
     */
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

    /**
     * Method under test: {@link StudentFileServiceImpl#deletefile(String)}
     */
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
