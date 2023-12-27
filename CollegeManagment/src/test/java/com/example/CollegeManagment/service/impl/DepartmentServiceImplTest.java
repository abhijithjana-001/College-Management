package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.config.DepartmentMapper;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {DepartmentServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentFileRepository departmentFileRepository;

    @Mock
    private DepartmentFileService departmentFileService;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;

    @Test
    void testCreateOrUpdate() throws IOException {
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

        when(departmentMapper.toEntity(Mockito.<DepartmentDto>any())).thenReturn(department);

        Optional<Department> ofResult = Optional.of(department);


        when(departmentRepo.existsByName(Mockito.<String>any())).thenReturn(false);
        when(departmentRepo.save(Mockito.<Department>any())).thenReturn(department);
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        when(departmentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(departmentImg);
        DepartmentDto buildResult = DepartmentDto.builder().name("Name").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any())).thenReturn(buildResult);

        // Act
        Responsedto<Department> actualCreateOrUpdateResult = departmentServiceImpl.createOrUpdate(
                "departmentdto",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L);

        // Assert
        assertEquals("Department added", actualCreateOrUpdateResult.getMessage());
        Department result = actualCreateOrUpdateResult.getResult();
        assertEquals(1L, result.getId().longValue());
        assertTrue(actualCreateOrUpdateResult.getSuccess());
        assertSame(department, result);
        assertSame(departmentImg, result.getDepartmentImg());
    }


    @Test
    void testFindAllDepartments() {
        // Arrange
        when(departmentRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Responsedto<List<Department>> actualFindAllDepartmentsResult = departmentServiceImpl.findAllDepartments(3, 10,
                "Sort By");

        // Assert
        verify(departmentRepo).findAll(Mockito.<Pageable>any());
        assertEquals("Department List", actualFindAllDepartmentsResult.getMessage());
        assertTrue(actualFindAllDepartmentsResult.getSuccess());
        assertTrue(actualFindAllDepartmentsResult.getResult().isEmpty());
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(departmentRepo).deleteById(Mockito.<Long>any());

        // Act
        Responsedto<Department> actualDeleteResult = departmentServiceImpl.delete(1L);

        // Assert
        verify(departmentRepo).deleteById(Mockito.<Long>any());
        assertEquals("Successfully Deleted", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }
}