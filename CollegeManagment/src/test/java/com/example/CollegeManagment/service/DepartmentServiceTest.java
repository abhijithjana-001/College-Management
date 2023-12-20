//package com.example.CollegeManagment.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import com.example.CollegeManagment.Exception.BadRequest;
//import com.example.CollegeManagment.config.DepartmentMapper;
//import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
//import com.example.CollegeManagment.dto.responsedto.Responsedto;
//import com.example.CollegeManagment.entity.Department;
//import com.example.CollegeManagment.repository.DepartmentRepo;
//import com.example.CollegeManagment.service.impl.DepartmentServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//@SpringBootTest
//public class DepartmentServiceTest {
//
//    @Mock
//    private DepartmentRepo departmentRepo;
//
//    @Mock
//    private DepartmentMapper departmentMapper;
//
//    @InjectMocks
//    private DepartmentServiceImpl departmentService;
//
//
//
//    @Test
//    @DisplayName("Test for creating a new department")
//    void createOrUpdate_NewDepartment() {
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setName("New Department");
//
//        when(departmentRepo.findByNameIgnoreCase("New Department")).thenReturn(null);
//
//        Responsedto response = departmentService.createOrUpdate(departmentDto, null);
//
//        assertTrue(response.getSuccess());
//        assertEquals("Department added", response.getMessage());
//    }
//
//    @Test
//    public void createOrUpdate_ExistingDepartmentName_ThrowsBadRequestException() {
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setName("ExistingDepartment");
//
//        when(departmentRepo.findByNameIgnoreCase(anyString())).thenReturn(new Department());
//
//        assertThrows(BadRequest.class, () -> departmentService.createOrUpdate(departmentDto, null));
//
//        verify(departmentRepo, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("Test for updating existing department")
//    void createOrUpdate_UpdateExistingDepartment() {
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setName("Updated Department");
//
//        Department existingDepartment = new Department();
//        existingDepartment.setId(1L);
//        existingDepartment.setName("Existing Department");
//
//        when(departmentRepo.findByNameIgnoreCase("Updated Department")).thenReturn(null);
//        when(departmentRepo.findById(1L)).thenReturn(Optional.of(existingDepartment));
//
//        Responsedto response = departmentService.createOrUpdate(departmentDto, 1L);
//
//        assertTrue(response.getSuccess());
//        assertEquals("Department added", response.getMessage());
//    }
//
//    @Test
//    @DisplayName("Test for finding all the departments ")
//    void findAllDepartments_ReturnsDepartmentList() {
//        // Arrange
//        int pageSize = 10;
//        int pageNumber = 0;
//        String sortBy = "name";
//
//        List<Department> departments = Collections.singletonList(new Department());
//        Page<Department> page = new org.springframework.data.domain.PageImpl<>(departments);
//
//        when(departmentRepo.findAll(any(Pageable.class))).thenReturn(page);
//
//        // Act
//        Responsedto<List<Department>> response = departmentService.findAllDepartments(pageSize, pageNumber, sortBy);
//
//        // Assert
//        assertEquals(true, response.getSuccess());
//        assertEquals("Department List", response.getMessage());
//        assertEquals(departments, response.getResult());
//    }
//
//
//    @Test
//    @DisplayName("Test for deleting a department")
//    void testDelete() {
//        long departmentId = 1L;
//
//        Responsedto<Department> response = departmentService.delete(departmentId);
//
//        assertNull(response.getResult());
//        assertTrue(response.getSuccess());
//    }
//}