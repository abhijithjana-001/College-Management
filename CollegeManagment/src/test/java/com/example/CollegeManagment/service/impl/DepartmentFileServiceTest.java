package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DepartmentFileServiceTest {
    @SpyBean
    private DepartmentFileRepository departmentFileRepository;

    @SpyBean
    private DepartmentFileService departmentFileService;


    @Test
    void testUpload() throws IOException {

        MockMultipartFile mockFile = new MockMultipartFile("test",
                "test.png",
                "image/png",
                "Hello, World!".getBytes());

        DepartmentFileEntity departmentFileEntity = DepartmentFileEntity.builder()
                .name(mockFile.getOriginalFilename())
                .type(mockFile.getContentType())
                .size(mockFile.getSize())
                .filePath("C:\\Users\\user432\\Desktop\\CollegeManagement\\files\\"+mockFile.getOriginalFilename())
                .created(LocalDateTime.now())
                .build();

        when(departmentFileRepository.existsByName(mockFile.getOriginalFilename())).thenReturn(false);

        // Act
        DepartmentFileEntity result = departmentFileService.upload(mockFile);

        // Assert
        verify(departmentFileRepository).save(any(DepartmentFileEntity.class));

        assertEquals(departmentFileEntity.getName(), result.getName());
        assertEquals(departmentFileEntity.getType(), result.getType());
        assertEquals(departmentFileEntity.getSize(), result.getSize());
        assertEquals(LocalDateTime.now().getDayOfMonth(), result.getCreated().getDayOfMonth());
        assertEquals(departmentFileEntity.getFilePath(),result.getFilePath());
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
        Department department = new Department();
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(department);
        departmentImg.setFilePath("/directory/image.jpg");
        departmentImg.setId(1L);
        departmentImg.setName("image.jpg");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Optional<DepartmentFileEntity> ofResult = Optional.of(departmentImg);
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        Responsedto actualDeleteFileResult = departmentFileService.deleteFile("image.jpg");
        verify(departmentFileRepository).findByName(Mockito.<String>any());
        assertEquals("File delete successfully!", actualDeleteFileResult.getMessage());
        assertNull(actualDeleteFileResult.getResult());
        assertFalse(actualDeleteFileResult.getSuccess());
    }

    @Test
    void testDeleteFile3() {
        Optional<DepartmentFileEntity> emptyResult = Optional.empty();
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> departmentFileService.deleteFile("foo.txt"));
        verify(departmentFileRepository).findByName(Mockito.<String>any());
    }
}