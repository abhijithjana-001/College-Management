package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.DepartmentMapper;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.repository.DepartmentRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DepartmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DepartmentServiceImplDiffblueTest {
    @MockBean
    private DepartmentMapper departmentMapper;

    @MockBean
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentServiceImpl departmentServiceImpl;

    /**
     * Method under test:
     * {@link DepartmentServiceImpl#createOrUpdate(DepartmentDto, Long)}
     */
    @Test
    void testCreateOrUpdate() {
        doNothing().when(departmentMapper).updateEntity(Mockito.<DepartmentDto>any(), Mockito.<Department>any());

        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        Optional<Department> ofResult = Optional.of(department);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());
        when(departmentRepo.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(department2);
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(BadRequest.class, () -> departmentServiceImpl.createOrUpdate(new DepartmentDto("Name"), 1L));
        verify(departmentMapper).updateEntity(Mockito.<DepartmentDto>any(), Mockito.<Department>any());
        verify(departmentRepo).findByNameIgnoreCase(Mockito.<String>any());
        verify(departmentRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link DepartmentServiceImpl#createOrUpdate(DepartmentDto, Long)}
     */
    @Test
    void testCreateOrUpdate2() {
        doNothing().when(departmentMapper).updateEntity(Mockito.<DepartmentDto>any(), Mockito.<Department>any());

        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        Optional<Department> ofResult = Optional.of(department);
        when(departmentRepo.findByNameIgnoreCase(Mockito.<String>any())).thenThrow(new ItemNotFound("Department added"));
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ItemNotFound.class, () -> departmentServiceImpl.createOrUpdate(new DepartmentDto("Name"), 1L));
        verify(departmentMapper).updateEntity(Mockito.<DepartmentDto>any(), Mockito.<Department>any());
        verify(departmentRepo).findByNameIgnoreCase(Mockito.<String>any());
        verify(departmentRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link DepartmentServiceImpl#createOrUpdate(DepartmentDto, Long)}
     */
    @Test
    void testCreateOrUpdate3() {
        Optional<Department> emptyResult = Optional.empty();
        when(departmentRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ItemNotFound.class, () -> departmentServiceImpl.createOrUpdate(new DepartmentDto("Name"), 1L));
        verify(departmentRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link DepartmentServiceImpl#createOrUpdate(DepartmentDto, Long)}
     */
    @Test
    void testCreateOrUpdate4() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setStudents(new HashSet<>());
        department.setTeachers(new HashSet<>());
        when(departmentMapper.toEntity(Mockito.<DepartmentDto>any())).thenReturn(department);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Name");
        department2.setStudents(new HashSet<>());
        department2.setTeachers(new HashSet<>());
        when(departmentRepo.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(department2);
        assertThrows(BadRequest.class, () -> departmentServiceImpl.createOrUpdate(new DepartmentDto("Name"), null));
        verify(departmentMapper).toEntity(Mockito.<DepartmentDto>any());
        verify(departmentRepo).findByNameIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DepartmentServiceImpl#findAllDepartments()}
     */
    @Test
    void testFindAllDepartments() {
        when(departmentRepo.findAll()).thenReturn(new ArrayList<>());
        Responsedto<List<Department>> actualFindAllDepartmentsResult = departmentServiceImpl.findAllDepartments();
        verify(departmentRepo).findAll();
        assertEquals("Department List", actualFindAllDepartmentsResult.getMessage());
        assertTrue(actualFindAllDepartmentsResult.getSuccess());
        assertTrue(actualFindAllDepartmentsResult.getResult().isEmpty());
    }

    /**
     * Method under test: {@link DepartmentServiceImpl#findAllDepartments()}
     */
    @Test
    void testFindAllDepartments2() {
        when(departmentRepo.findAll()).thenThrow(new BadRequest("Department List"));
        assertThrows(BadRequest.class, () -> departmentServiceImpl.findAllDepartments());
        verify(departmentRepo).findAll();
    }

    /**
     * Method under test: {@link DepartmentServiceImpl#delete(Long)}
     */
    @Test
    void testDelete() {
        doNothing().when(departmentRepo).deleteById(Mockito.<Long>any());
        Responsedto<Department> actualDeleteResult = departmentServiceImpl.delete(1L);
        verify(departmentRepo).deleteById(Mockito.<Long>any());
        assertEquals("Successfully Deleted", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }

    /**
     * Method under test: {@link DepartmentServiceImpl#delete(Long)}
     */
    @Test
    void testDelete2() {
        doThrow(new BadRequest("Successfully Deleted")).when(departmentRepo).deleteById(Mockito.<Long>any());
        assertThrows(BadRequest.class, () -> departmentServiceImpl.delete(1L));
        verify(departmentRepo).deleteById(Mockito.<Long>any());
    }
}
