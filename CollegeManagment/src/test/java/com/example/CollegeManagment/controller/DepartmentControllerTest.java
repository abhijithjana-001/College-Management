package com.example.CollegeManagment.controller;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Student;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.service.DepartmentService;
import com.example.CollegeManagment.service.impl.DepartmentFileService;
import com.example.CollegeManagment.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DepartmentControllerTest {

    @SpyBean
    private DepartmentServiceImpl departmentService;

    @SpyBean
    private DepartmentFileService departmentFileService;

    @SpyBean
    private DepartmentController departmentController;

    @SpyBean
    DepartmentRepo departmentRepo;

    @Test
    void testAddDepartment() throws IOException {

        // Arrange
        DepartmentServiceImpl departmentService = mock(DepartmentServiceImpl.class);
        when(departmentService.createOrUpdate(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any()))
                .thenReturn(new Responsedto<>());
        DepartmentController departmentController = new DepartmentController(departmentService);

        // Act
        ResponseEntity<Responsedto<Department>> departments = departmentController.addDepartment(
                "departmentDto",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

        // Assert
        verify(departmentService).createOrUpdate(Mockito.<String>any(), Mockito.<MultipartFile>any(), Mockito.<Long>any());
        assertEquals(200, departments.getStatusCodeValue());
        assertTrue(departments.hasBody());
        assertTrue(departments.getHeaders().isEmpty());
    }


    @Test
    void testListDepartment() throws Exception {
        // Arrange
        Page page=new PageImpl(Arrays.asList(new Department(),new Department()));

        doReturn(page).when(departmentRepo).findAll(any(Pageable.class));
        // Act
        ResponseEntity<Responsedto<List<Department>>> listDepartment = departmentController
                .findAllDepartments(1,2,"name");

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), listDepartment.getStatusCode());
        assertTrue(listDepartment.hasBody());
        assertEquals(2, listDepartment.getBody().getResult().size());

    }

    @Test
    void testDeleteDepartment() {
        //arrange
        Long departmentId = 1L;
        Responsedto expectedResponse = new Responsedto(true, "Successfully Deleted", null);

        when(departmentService.delete(departmentId)).thenReturn(expectedResponse);

        //act
        ResponseEntity<Responsedto<Department>> responseEntity = departmentController.delete(departmentId);

        verify(departmentService, times(1)).delete(departmentId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testUpdateDepartment() throws Exception {
        // Arrange
        Responsedto<Department> responsedto=new Responsedto<>(true,"department added or updated successful",new Department());

        doReturn(responsedto).when(departmentService).createOrUpdate(anyString(),any(MultipartFile.class),anyLong());

        // Act
        ResponseEntity<Responsedto<Department>> responsedtoResponseEntity = departmentController.update(1L, "departmentDto", new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

        //        assert
        assertEquals(HttpStatusCode.valueOf(200),responsedtoResponseEntity.getStatusCode());
        assertEquals("department added or updated successful",responsedtoResponseEntity.getBody().getMessage());
        assertTrue(responsedtoResponseEntity.getBody().getSuccess());

    }

    @Test
    void testFindDepartment() throws Exception {
        // Arrange

        doReturn(Optional.of(new Department())).when(departmentRepo).findById(anyLong());
        // Act
        ResponseEntity<Responsedto<Department>> findDepartment = departmentController.findDepartment(1L);
        //assert
        assertEquals(HttpStatusCode.valueOf(200), findDepartment.getStatusCode());
        assertTrue(findDepartment.hasBody());

    }
}