package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.config.DepartmentMapper;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DepartmentServiceImpl.class})
//@ExtendWith(SpringExtension.class)
@SpringBootTest
class DepartmentServiceImplTest {
    @MockBean
    private DepartmentFileService departmentFileService;

    @MockBean
    private DepartmentMapper departmentMapper;

    @MockBean
    private DepartmentRepo departmentRepo;

    @SpyBean
    private DepartmentServiceImpl departmentServiceImpl;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrUpdate() throws IOException {
        DepartmentFileEntity departmentImg = new DepartmentFileEntity();
        departmentImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        departmentImg.setDepartment(new Department());
        departmentImg.setFilePath("/directory/image.jpg");
        departmentImg.setId(1L);
        departmentImg.setName("image");
        departmentImg.setSize(3L);
        departmentImg.setType("Type");

        Department department = new Department();
        department.setDepartmentImg(departmentImg);
        department.setId(1L);
        department.setName("department");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());

        when(departmentFileService.upload(Mockito.<MultipartFile>any())).thenReturn(departmentImg);

        Optional<Department> ofResult = Optional.of(department);
        when(departmentRepo.existsByName(Mockito.<String>any())).thenReturn(false);
        when(departmentRepo.save(Mockito.<Department>any())).thenReturn(department);
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        when(departmentMapper.toEntity(Mockito.<DepartmentDto>any())).thenReturn(department);
        DepartmentDto buildResult = DepartmentDto.builder().name("department").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<DepartmentDto>>any())).thenReturn(buildResult);
        Responsedto<Department> actualCreateOrUpdateResult = departmentServiceImpl.createOrUpdate(
                "departmentDto",
                new MockMultipartFile("Name", new ByteArrayInputStream("toByte".getBytes("UTF-8"))), 1L);
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
        assertSame(department, result);
        assertSame(departmentImg, result.getDepartmentImg());
    }

    @Test
    void testFindAllDepartments() {
        when(departmentRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Responsedto<List<Department>> actualFindAllDepartmentsResult = departmentServiceImpl.findAllDepartments(3, 10,
                "Sort By");
        verify(departmentRepo).findAll(Mockito.<Pageable>any());
        assertEquals("Department List", actualFindAllDepartmentsResult.getMessage());
        assertTrue(actualFindAllDepartmentsResult.getSuccess());
        assertTrue(actualFindAllDepartmentsResult.getResult().isEmpty());
    }

    @Test
    void testFindAllDepartments2() {
        when(departmentRepo.findAll(Mockito.<Pageable>any())).thenThrow(new BadRequest("Msg"));
        assertThrows(BadRequest.class, () -> departmentServiceImpl.findAllDepartments(3, 10, "Sort By"));
        verify(departmentRepo).findAll(Mockito.<Pageable>any());
    }

    @Test
    void testDelete() {
        doNothing().when(departmentRepo).deleteById(Mockito.<Long>any());
        Responsedto<Department> actualDeleteResult = departmentServiceImpl.delete(1L);
        verify(departmentRepo).deleteById(Mockito.<Long>any());
        assertEquals("Successfully Deleted", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }

    @Test
    void testDelete2() {
        doThrow(new BadRequest("failed to delete")).when(departmentRepo).deleteById(Mockito.<Long>any());
        assertThrows(BadRequest.class, () -> departmentServiceImpl.delete(1L));
        verify(departmentRepo).deleteById(Mockito.<Long>any());
    }
}
