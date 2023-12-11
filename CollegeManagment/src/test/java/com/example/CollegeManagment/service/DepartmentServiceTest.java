package com.example.CollegeManagment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentServiceImpl departmentService;



    @Test
    @DisplayName("Test for creating a new department")
    void createOrUpdate_NewDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("New Department");

        when(departmentRepo.findByNameIgnoreCase("New Department")).thenReturn(null);

        Responsedto response = departmentService.createOrUpdate(departmentDto);

        assertTrue(response.getSuccess());
        assertEquals("Department added", response.getMessage());
        assertNotNull(response.getResult());
    }

    @Test
    @DisplayName("Test for bad request exception ")
    void createOrUpdate_ThrowBadRequestException() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Existing Department");
        Department existingDepartment = new Department();
        existingDepartment.setId(1L);
        existingDepartment.setName("Existing Department");

        when(departmentRepo.findByNameIgnoreCase("Existing Department")).thenReturn(existingDepartment);

        BadRequest exception = assertThrows(BadRequest.class, () -> {
            departmentService.createOrUpdate(departmentDto);
        });

        assertEquals("Department name already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Test for updating existing department")
    void createOrUpdate_UpdateExistingDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setName("Updated Department");

        Department existingDepartment = new Department();
        existingDepartment.setId(1L);
        existingDepartment.setName("Existing Department");

        when(departmentRepo.findByNameIgnoreCase("Updated Department")).thenReturn(null);
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(existingDepartment));

        Responsedto response = departmentService.createOrUpdate(departmentDto);

        assertTrue(response.getSuccess());
        assertEquals("Updated Successfully", response.getMessage());
        assertNotNull(response.getResult());
    }

    @Test
    @DisplayName("Test for finding all the departmens ")
    void testFindAllDepartments() {
        List<Department> departments = new ArrayList<>();
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("Department 1");
        departments.add(department1);

        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("Department 2");
        departments.add(department2);

        when(departmentRepo.findAll()).thenReturn(departments);

        Responsedto<List<Department>> response = departmentService.findAllDepartments();

        assertTrue(response.getSuccess());
        assertEquals("Department List", response.getMessage());
        assertEquals(departments, response.getResult());
    }

    @Test
    @DisplayName("Test for deleting a department")
    void testDelete() {
        long departmentId = 1L;

        Responsedto<Department> response = departmentService.delete(departmentId);

        assertNull(response.getResult());
        assertTrue(response.getSuccess());
    }
}

