package com.example.CollegeManagment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentServiceImpl departmentService;


    @Test
    public void testAddDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Test Department");

        Department savedDepartment = new Department();
        savedDepartment.setId(1L);
        savedDepartment.setName("Test Department");

        when(departmentRepo.save(any(Department.class))).thenReturn(savedDepartment);

        Responsedto response = departmentService.addDepartment(departmentDto);

        assertTrue(response.getSuccess());
        assertEquals("Department added", response.getMessage());
//        assertEquals(savedDepartment, response.getResult());
    }

    @Test
    public void testFindAllDepartments() {
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
    public void testUpdateDepartment() {
        long departmentId = 1L;
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Updated Department");

        Department existingDepartment = new Department();
        existingDepartment.setId(departmentId);
        existingDepartment.setName("Old Department");

        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepo.save(any(Department.class))).thenReturn(existingDepartment);

        Responsedto<Department> response = departmentService.updateDepartment(departmentId, departmentDto);

        assertTrue(response.getSuccess());
        assertEquals("Updated Successfully", response.getMessage());
        assertEquals(existingDepartment, response.getResult());
    }

    @Test
    public void testDelete() {
        long departmentId = 1L;

        Responsedto<Department> response = departmentService.delete(departmentId);

        assertNull(response.getResult());
        assertTrue(response.getSuccess()); // Assuming success even if data is null
    }
}

