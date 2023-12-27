package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = {DepartmentFileService.class})
@ExtendWith(MockitoExtension.class)
class DepartmentFileServiceTest {
    @Mock
    private DepartmentFileRepository departmentFileRepository;

    @InjectMocks
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
        Optional<DepartmentFileEntity> emptyResult = Optional.empty();
        when(departmentFileRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ItemNotFound.class, () -> departmentFileService.deleteFile("foo.txt"));
        verify(departmentFileRepository).findByName(Mockito.<String>any());
    }
}
