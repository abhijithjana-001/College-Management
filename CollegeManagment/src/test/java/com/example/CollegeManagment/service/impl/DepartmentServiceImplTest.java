package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import com.example.CollegeManagment.repository.DepartmentRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartmentServiceImplTest {

    @SpyBean
    private DepartmentServiceImpl departmentService;

    @SpyBean
    private DepartmentRepo departmentRepo;

    @SpyBean
    private DepartmentFileRepository departmentFileRepository;

    @SpyBean
    private DepartmentFileService departmentFileService;

    @Test
    void testAddOrUpdate_Add() throws IOException{
        //arrange
        Department department = new Department();
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("department");

        String departmentDto = "{\"name\":\"department\"}";

        MultipartFile file = new MockMultipartFile("image",
                "image.jpg",
                "image",
                "toByte".getBytes(StandardCharsets.UTF_8)
        );

        doReturn(department).when(departmentRepo).save(any(Department.class));
        doReturn(false).when(departmentRepo).existsByName(Mockito.anyString());

        //act
        Responsedto<Department> responseDto = departmentService.createOrUpdate(departmentDto, file, null);

        //assert
        assertTrue(responseDto.getSuccess());
        assertEquals("Department added", responseDto.getMessage());
    }

    @Test
    void addOrUpdate_Update(){
        //arrange
        DepartmentFileEntity departmentFileEntity = new DepartmentFileEntity();
        departmentFileEntity.setName("image");

        Department department = new Department();
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        department.setId(1L);
        department.setName("department");
        department.setDepartmentImg(departmentFileEntity);

        doReturn(departmentFileEntity).when(departmentRepo.existsByName(Mockito.anyString()));
        doReturn(true).when(departmentRepo.existsById(Mockito.anyLong()));
        doReturn(Optional.of(department)).when(departmentRepo.findById(anyLong()));
        doReturn(department).when(departmentRepo).save(Mockito.any(Department.class));
        doNothing().when(departmentFileService).deleteFile(anyString());

        //act
        Responsedto<Department> responseDto = departmentService.createOrUpdate(
                "{\"id\":1,\"name\":\"department_1\"}",
                new MockMultipartFile("image",
                        "image.jpg",
                        "image",
                        "toByte".getBytes(StandardCharsets.UTF_8)
                ),1L);

        System.out.println(responseDto.getResult());

        //assert
        assertTrue(responseDto.getSuccess());
    }
}
