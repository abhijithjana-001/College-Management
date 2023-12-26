package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ContextConfiguration(classes = {DepartmentFileService.class})
@ExtendWith(SpringExtension.class)
class DepartmentFileServiceTest {
    @MockBean
    private DepartmentFileRepository departmentFileRepository;

    @Autowired
    private DepartmentFileService departmentFileService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;


    @Test
    void testUpload() throws IOException {

        String uploadDir = "${file.path}";
        MockMultipartFile mockFile = new MockMultipartFile(
                "testFile",
                "test.png",
                "image/png",
                "Hello, World!".getBytes()
        );

        Path expectedFilePath = Paths.get(uploadDir, mockFile.getOriginalFilename());

        when(departmentFileRepository.save(any(DepartmentFileEntity.class)))
                .thenAnswer(invocation -> {
                    DepartmentFileEntity savedEntity = invocation.getArgument(0);
                    savedEntity.setId(1L);
                    return savedEntity;
                });

        // Act
        DepartmentFileEntity result = departmentFileService.upload(mockFile);

        // Assert
        verify(departmentFileRepository).save(any(DepartmentFileEntity.class));

        assertEquals(mockFile.getOriginalFilename(), result.getName());
        assertEquals(mockFile.getContentType(), result.getType());
        assertEquals(mockFile.getSize(), result.getSize());
        assertEquals(expectedFilePath.toString(), result.getFilePath());
        assertEquals(LocalDateTime.now().getDayOfMonth(), result.getCreated().getDayOfMonth());
    }

    @Test
    void testFindByName() throws IOException {
        // Arrange
        Optional<DepartmentFileEntity> emptyResult = Optional.empty();
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> departmentFileService.findByName("Name"));
        verify(departmentFileRepository).findByName(Mockito.<String>any());
    }

    @Test
    void testDeleteFile() {
        // Arrange
        Department department = new Department();
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(department);
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentFileEntity = new DepartmentFileEntity();
        departmentFileEntity.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentFileEntity.setDepartment(department2);
        departmentFileEntity.setFilePath("/directory/foo.txt");
        departmentFileEntity.setId(1L);
        departmentFileEntity.setName("Name");
        departmentFileEntity.setSize(3L);
        departmentFileEntity.setType("Type");
        Optional<DepartmentFileEntity> ofResult = Optional.of(departmentFileEntity);
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Responsedto actualDeleteFileResult = departmentFileService.deleteFile("foo.txt");

        // Assert
        verify(departmentFileRepository).findByName(Mockito.<String>any());
        assertEquals("File delete successfully!", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
    }

    @Test
    void testDeleteFile2() {
        // Arrange
        Department department = new Department();
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(department);
        departmentImg.setFilePath("/directory/foo.txt");
        departmentImg.setId(1L);
        departmentImg.setName("Name");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());
        DepartmentFileEntity departmentFileEntity = mock(DepartmentFileEntity.class);

        when(departmentFileEntity.getFilePath()).thenReturn("/directory/foo.txt");
        doNothing().when(departmentFileEntity).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(departmentFileEntity).setDepartment(Mockito.<Department>any());
        doNothing().when(departmentFileEntity).setFilePath(Mockito.<String>any());
        doNothing().when(departmentFileEntity).setId(Mockito.<Long>any());
        doNothing().when(departmentFileEntity).setName(Mockito.<String>any());
        doNothing().when(departmentFileEntity).setSize(Mockito.<Long>any());
        doNothing().when(departmentFileEntity).setType(Mockito.<String>any());

        departmentFileEntity.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentFileEntity.setDepartment(department2);
        departmentFileEntity.setFilePath("/directory/foo.txt");
        departmentFileEntity.setId(1L);
        departmentFileEntity.setName("Name");
        departmentFileEntity.setSize(3L);
        departmentFileEntity.setType("Type");
        Optional<DepartmentFileEntity> ofResult = Optional.of(departmentFileEntity);
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Responsedto actualDeleteFileResult = departmentFileService.deleteFile("foo.txt");

        // Assert
        verify(departmentFileEntity).getFilePath();
        verify(departmentFileEntity).setCreated(Mockito.<LocalDateTime>any());
        verify(departmentFileEntity).setDepartment(Mockito.<Department>any());
        verify(departmentFileEntity).setFilePath(Mockito.<String>any());
        verify(departmentFileEntity).setId(Mockito.<Long>any());
        verify(departmentFileEntity).setName(Mockito.<String>any());
        verify(departmentFileEntity).setSize(Mockito.<Long>any());
        verify(departmentFileEntity).setType(Mockito.<String>any());
        verify(departmentFileRepository).findByName(Mockito.<String>any());
        assertEquals("File delete successfully!", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
    }


    @Test
    void testDeleteFile3() {
        // Arrange
        Optional<DepartmentFileEntity> emptyResult = Optional.empty();
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> departmentFileService.deleteFile("foo.txt"));
        verify(departmentFileRepository).findByName(Mockito.<String>any());
    }
}
