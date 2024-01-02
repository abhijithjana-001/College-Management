package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import com.example.CollegeManagment.repository.DepartmentRepo;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
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
    void testAddOrUpdate_Add() throws IOException {
        //arrange
        Department department = new Department();
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        department.setDepartmentImg(new DepartmentFileEntity());
        department.setId(1L);
        department.setName("department");

        String departmentDto = "{\"name\":\"department\"}";

        MultipartFile file = new MockMultipartFile("image", "image.jpg", "image",
                "toByte".getBytes(StandardCharsets.UTF_8));

        doReturn(department).when(departmentRepo).save(any(Department.class));
        doReturn(false).when(departmentRepo).existsByName(Mockito.anyString());

        //act
        Responsedto<Department> responseDto = departmentService.createOrUpdate(departmentDto, file, null);

        //assert
        assertTrue(responseDto.getSuccess());
        assertEquals("Department added", responseDto.getMessage());
        assertNotNull(responseDto.getResult());
    }

    @Test
    void addOrUpdate_Update() {
        //arrange
        Department department = new Department();
        department.setName("department");
        department.setId(1L);

        String departmentDto = "{\"name\":\"department\"}";

        MultipartFile file = new MockMultipartFile("image", "image.jpg", "image",
                "toByte".getBytes(StandardCharsets.UTF_8));

        //act
        when(departmentRepo.findById(anyLong())).thenReturn(Optional.of(department));
        when(departmentRepo.existsByName(anyString())).thenReturn(false);
        Responsedto<Department> responsedto = departmentService.createOrUpdate(departmentDto, file, 1L);

        //assert
        assertTrue(responsedto.getSuccess());
        assertEquals("Department added", responsedto.getMessage());
        assertNotNull(responsedto.getResult());
    }

    @Test
    public void listAllDepartment() {
        // Arrange
        Integer pageSize = 10;
        Integer pageNumber = 0;
        String sortBy = "name";

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Department> pageDepartment = Mockito.mock(Page.class);
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, "name1"));
        departments.add(new Department(2L, "name2"));

        Mockito.when(pageDepartment.getContent()).thenReturn(departments);
        Mockito.when(departmentRepo.findAll(pageable)).thenReturn(pageDepartment);

        // Act
        Responsedto<List<Department>> response = departmentService.findAllDepartments(pageSize, pageNumber, sortBy);

        // Assert
        assertTrue(response.getSuccess());
        assertEquals("Department List", response.getMessage());
        assertNotNull(response.getResult());

        List<Department> resultDepartments = response.getResult();
        assertEquals(departments.size(), resultDepartments.size());
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testDelete() throws IOException {
        // Arrange
        doNothing().when(departmentRepo).deleteById(Mockito.<Long>any());

        // Act
        Responsedto actualDeleteResult = departmentService.delete(1L);

        // Assert
        verify(departmentRepo).deleteById(Mockito.<Long>any());
        Assertions.assertEquals("Successfully Deleted", actualDeleteResult.getMessage());
        Assertions.assertNull(actualDeleteResult.getResult());
        Assertions.assertTrue(actualDeleteResult.getSuccess());
    }

}
