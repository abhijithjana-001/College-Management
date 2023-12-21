package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {DepartmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DepartmentServiceImplTest {
    @MockBean
    private DepartmentFileRepository departmentFileRepository;

    @MockBean
    private DepartmentFileService departmentFileService;

    @MockBean
    private DepartmentMapper departmentMapper;

    @MockBean
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentServiceImpl departmentServiceImpl;

    @MockBean
    private ObjectMapper objectMapper;


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

        Department department2 = new Department();
        department2.setDepartmentImg(departmentImg);
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg2 = new DepartmentFileEntity();
        departmentImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg2.setDepartment(department2);
        departmentImg2.setFilePath("/directory/foo.txt");
        departmentImg2.setId(1L);
        departmentImg2.setName("Name");
        departmentImg2.setSize(3L);
        departmentImg2.setType("Type");

        Department department3 = new Department();
        department3.setDepartmentImg(departmentImg2);
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());
        when(departmentMapper.toEntity(Mockito.<DepartmentDto>any())).thenReturn(department3);

        DepartmentFileEntity departmentImg3 = new DepartmentFileEntity();
        departmentImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg3.setDepartment(new Department());
        departmentImg3.setFilePath("/directory/foo.txt");
        departmentImg3.setId(1L);
        departmentImg3.setName("Name");
        departmentImg3.setSize(3L);
        departmentImg3.setType("Type");

        Department department4 = new Department();
        department4.setDepartmentImg(departmentImg3);
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg4 = new DepartmentFileEntity();
        departmentImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg4.setDepartment(department4);
        departmentImg4.setFilePath("/directory/foo.txt");
        departmentImg4.setId(1L);
        departmentImg4.setName("Name");
        departmentImg4.setSize(3L);
        departmentImg4.setType("Type");

        Department department5 = new Department();
        department5.setDepartmentImg(departmentImg4);
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());
        Optional<Department> ofResult = Optional.of(department5);
        when(departmentRepo.existsByName(Mockito.<String>any())).thenReturn(true);
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DepartmentFileEntity departmentImg5 = new DepartmentFileEntity();
        departmentImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg5.setDepartment(new Department());
        departmentImg5.setFilePath("/directory/foo.txt");
        departmentImg5.setId(1L);
        departmentImg5.setName("Name");
        departmentImg5.setSize(3L);
        departmentImg5.setType("Type");

        Department department6 = new Department();
        department6.setDepartmentImg(departmentImg5);
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg6 = new DepartmentFileEntity();
        departmentImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg6.setDepartment(department6);
        departmentImg6.setFilePath("/directory/foo.txt");
        departmentImg6.setId(1L);
        departmentImg6.setName("Name");
        departmentImg6.setSize(3L);
        departmentImg6.setType("Type");

        Department department7 = new Department();
        department7.setDepartmentImg(departmentImg6);
        department7.setId(1L);
        department7.setName("Name");
        department7.setStudents(new HashSet<>());
        department7.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentFileEntity = new DepartmentFileEntity();
        departmentFileEntity.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentFileEntity.setDepartment(department7);
        departmentFileEntity.setFilePath("/directory/foo.txt");
        departmentFileEntity.setId(1L);
        departmentFileEntity.setName("Name");
        departmentFileEntity.setSize(3L);
        departmentFileEntity.setType("Type");
        when(departmentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(departmentFileEntity);
        DepartmentDto buildResult = DepartmentDto.builder().name("Name").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(BadRequest.class, () -> departmentServiceImpl.createOrUpdate("alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(departmentMapper).toEntity(Mockito.<DepartmentDto>any());
        verify(departmentRepo).existsByName(Mockito.<String>any());
        verify(departmentFileService).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any());
        verify(departmentRepo).findById(Mockito.<Long>any());
    }


    @Test
    void testCreateOrUpdate2() throws IOException {
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

        DepartmentFileEntity departmentImg2 = new DepartmentFileEntity();
        departmentImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg2.setDepartment(department2);
        departmentImg2.setFilePath("/directory/foo.txt");
        departmentImg2.setId(1L);
        departmentImg2.setName("Name");
        departmentImg2.setSize(3L);
        departmentImg2.setType("Type");

        Department department3 = new Department();
        department3.setDepartmentImg(departmentImg2);
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());
        when(departmentMapper.toEntity(Mockito.<DepartmentDto>any())).thenReturn(department3);

        DepartmentFileEntity departmentImg3 = new DepartmentFileEntity();
        departmentImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg3.setDepartment(new Department());
        departmentImg3.setFilePath("/directory/foo.txt");
        departmentImg3.setId(1L);
        departmentImg3.setName("Name");
        departmentImg3.setSize(3L);
        departmentImg3.setType("Type");

        Department department4 = new Department();
        department4.setDepartmentImg(departmentImg3);
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg4 = new DepartmentFileEntity();
        departmentImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg4.setDepartment(department4);
        departmentImg4.setFilePath("/directory/foo.txt");
        departmentImg4.setId(1L);
        departmentImg4.setName("Name");
        departmentImg4.setSize(3L);
        departmentImg4.setType("Type");

        Department department5 = new Department();
        department5.setDepartmentImg(departmentImg4);
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());
        Optional<Department> ofResult = Optional.of(department5);
        when(departmentRepo.existsByName(Mockito.<String>any())).thenThrow(new BadRequest("Department added"));
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DepartmentFileEntity departmentImg5 = new DepartmentFileEntity();
        departmentImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg5.setDepartment(new Department());
        departmentImg5.setFilePath("/directory/foo.txt");
        departmentImg5.setId(1L);
        departmentImg5.setName("Name");
        departmentImg5.setSize(3L);
        departmentImg5.setType("Type");

        Department department6 = new Department();
        department6.setDepartmentImg(departmentImg5);
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg6 = new DepartmentFileEntity();
        departmentImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg6.setDepartment(department6);
        departmentImg6.setFilePath("/directory/foo.txt");
        departmentImg6.setId(1L);
        departmentImg6.setName("Name");
        departmentImg6.setSize(3L);
        departmentImg6.setType("Type");

        Department department7 = new Department();
        department7.setDepartmentImg(departmentImg6);
        department7.setId(1L);
        department7.setName("Name");
        department7.setStudents(new HashSet<>());
        department7.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentFileEntity = new DepartmentFileEntity();
        departmentFileEntity.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentFileEntity.setDepartment(department7);
        departmentFileEntity.setFilePath("/directory/foo.txt");
        departmentFileEntity.setId(1L);
        departmentFileEntity.setName("Name");
        departmentFileEntity.setSize(3L);
        departmentFileEntity.setType("Type");
        when(departmentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(departmentFileEntity);
        DepartmentDto buildResult = DepartmentDto.builder().name("Name").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(BadRequest.class, () -> departmentServiceImpl.createOrUpdate("alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L));
        verify(departmentMapper).toEntity(Mockito.<DepartmentDto>any());
        verify(departmentRepo).existsByName(Mockito.<String>any());
        verify(departmentFileService).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any());
        verify(departmentRepo).findById(Mockito.<Long>any());
    }


    @Test
    void testCreateOrUpdate3() throws IOException {
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

        DepartmentFileEntity departmentImg2 = new DepartmentFileEntity();
        departmentImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg2.setDepartment(department2);
        departmentImg2.setFilePath("/directory/foo.txt");
        departmentImg2.setId(1L);
        departmentImg2.setName("Name");
        departmentImg2.setSize(3L);
        departmentImg2.setType("Type");

        Department department3 = new Department();
        department3.setDepartmentImg(departmentImg2);
        department3.setId(1L);
        department3.setName("Name");
        department3.setStudents(new HashSet<>());
        department3.setTeachers(new HashSet<>());
        when(departmentMapper.toEntity(Mockito.<DepartmentDto>any())).thenReturn(department3);

        DepartmentFileEntity departmentImg3 = new DepartmentFileEntity();
        departmentImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg3.setDepartment(new Department());
        departmentImg3.setFilePath("/directory/foo.txt");
        departmentImg3.setId(1L);
        departmentImg3.setName("Name");
        departmentImg3.setSize(3L);
        departmentImg3.setType("Type");

        Department department4 = new Department();
        department4.setDepartmentImg(departmentImg3);
        department4.setId(1L);
        department4.setName("Name");
        department4.setStudents(new HashSet<>());
        department4.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg4 = new DepartmentFileEntity();
        departmentImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg4.setDepartment(department4);
        departmentImg4.setFilePath("/directory/foo.txt");
        departmentImg4.setId(1L);
        departmentImg4.setName("Name");
        departmentImg4.setSize(3L);
        departmentImg4.setType("Type");

        Department department5 = new Department();
        department5.setDepartmentImg(departmentImg4);
        department5.setId(1L);
        department5.setName("Name");
        department5.setStudents(new HashSet<>());
        department5.setTeachers(new HashSet<>());
        Optional<Department> ofResult = Optional.of(department5);

        Department department6 = new Department();
        department6.setDepartmentImg(new DepartmentFileEntity());
        department6.setId(1L);
        department6.setName("Name");
        department6.setStudents(new HashSet<>());
        department6.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg5 = new DepartmentFileEntity();
        departmentImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg5.setDepartment(department6);
        departmentImg5.setFilePath("/directory/foo.txt");
        departmentImg5.setId(1L);
        departmentImg5.setName("Name");
        departmentImg5.setSize(3L);
        departmentImg5.setType("Type");

        Department department7 = new Department();
        department7.setDepartmentImg(departmentImg5);
        department7.setId(1L);
        department7.setName("Name");
        department7.setStudents(new HashSet<>());
        department7.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg6 = new DepartmentFileEntity();
        departmentImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg6.setDepartment(department7);
        departmentImg6.setFilePath("/directory/foo.txt");
        departmentImg6.setId(1L);
        departmentImg6.setName("Name");
        departmentImg6.setSize(3L);
        departmentImg6.setType("Type");

        Department department8 = new Department();
        department8.setDepartmentImg(departmentImg6);
        department8.setId(1L);
        department8.setName("Name");
        department8.setStudents(new HashSet<>());
        department8.setTeachers(new HashSet<>());
        when(departmentRepo.existsByName(Mockito.<String>any())).thenReturn(false);
        when(departmentRepo.save(Mockito.<Department>any())).thenReturn(department8);
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DepartmentFileEntity departmentImg7 = new DepartmentFileEntity();
        departmentImg7.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg7.setDepartment(new Department());
        departmentImg7.setFilePath("/directory/foo.txt");
        departmentImg7.setId(1L);
        departmentImg7.setName("Name");
        departmentImg7.setSize(3L);
        departmentImg7.setType("Type");

        Department department9 = new Department();
        department9.setDepartmentImg(departmentImg7);
        department9.setId(1L);
        department9.setName("Name");
        department9.setStudents(new HashSet<>());
        department9.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentImg8 = new DepartmentFileEntity();
        departmentImg8.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg8.setDepartment(department9);
        departmentImg8.setFilePath("/directory/foo.txt");
        departmentImg8.setId(1L);
        departmentImg8.setName("Name");
        departmentImg8.setSize(3L);
        departmentImg8.setType("Type");

        Department department10 = new Department();
        department10.setDepartmentImg(departmentImg8);
        department10.setId(1L);
        department10.setName("Name");
        department10.setStudents(new HashSet<>());
        department10.setTeachers(new HashSet<>());

        DepartmentFileEntity departmentFileEntity = new DepartmentFileEntity();
        departmentFileEntity.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentFileEntity.setDepartment(department10);
        departmentFileEntity.setFilePath("/directory/foo.txt");
        departmentFileEntity.setId(1L);
        departmentFileEntity.setName("Name");
        departmentFileEntity.setSize(3L);
        departmentFileEntity.setType("Type");
        when(departmentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(departmentFileEntity);
        DepartmentDto buildResult = DepartmentDto.builder().name("Name").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any())).thenReturn(buildResult);

        // Act
        Responsedto<Department> actualCreateOrUpdateResult = departmentServiceImpl.createOrUpdate(
                "alice.liddell@example.org",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L);

        // Assert
        verify(departmentMapper).toEntity(Mockito.<DepartmentDto>any());
        verify(departmentRepo).existsByName(Mockito.<String>any());
        verify(departmentFileService).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any());
        verify(departmentRepo).findById(Mockito.<Long>any());
        verify(departmentRepo).save(Mockito.<Department>any());
        assertEquals("Department added", actualCreateOrUpdateResult.getMessage());
        Department result = actualCreateOrUpdateResult.getResult();
        assertEquals(1L, result.getId().longValue());
        assertTrue(actualCreateOrUpdateResult.getSuccess());
        assertSame(department3, result);
        assertSame(departmentFileEntity, result.getDepartmentImg());
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
    void testFindAllDepartments2() {
        // Arrange
        when(departmentRepo.findAll(Mockito.<Pageable>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> departmentServiceImpl.findAllDepartments(3, 10, "Sort By"));
        verify(departmentRepo).findAll(Mockito.<Pageable>any());
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


    @Test
    void testDelete2() {
        // Arrange
        doThrow(new RuntimeException("Successfully Deleted")).when(departmentRepo).deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> departmentServiceImpl.delete(1L));
        verify(departmentRepo).deleteById(Mockito.<Long>any());
    }
}
