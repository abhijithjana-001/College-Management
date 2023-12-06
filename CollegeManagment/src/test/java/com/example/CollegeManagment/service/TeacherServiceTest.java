package com.example.CollegeManagment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepo teacherRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    public void testAddTeacher() {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Test Teacher");
        Department department = new Department();
        department.setId(1L);
        department.setName("Test Department");
        HashSet<Department> set=new HashSet();
        set.add(department);
        teacherRequestDTO.setDepartment(set);

        Teacher savedTeacher = new Teacher();

        savedTeacher.setName("Test Teacher");
        savedTeacher.setDepartments(set);


        when(teacherRepo.save(any(Teacher.class))).thenReturn(savedTeacher);

        Responsedto<Teacher> response = teacherService.addTeacher(teacherRequestDTO);


        assertTrue(response.getSuccess());
        assertEquals("Added Successfully", response.getMessage());

        assertEquals(savedTeacher.getTid(), response.getResult().getTid());
    }

    @Test
    public void testFindAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setTid(1L);
        teacher1.setName("Teacher 1");
        teachers.add(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setTid(2L);
        teacher2.setName("Teacher 2");
        teachers.add(teacher2);

        when(teacherRepo.findAll()).thenReturn(teachers);

        Responsedto<List<Teacher>> response = teacherService.findAll();

        assertTrue(response.getSuccess());
        assertEquals("Teachers List", response.getMessage());
        assertEquals(teachers, response.getResult());
    }

    @Test
    public void testUpdateTeacher() {
        long teacherId = 1L;
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Updated Teacher");
        Department department = new Department();
        department.setId(1L);
        department.setName("Updated Department");
        HashSet<Department> set=new HashSet();
        set.add(department);

        teacherRequestDTO.setDepartment(set);

        Teacher existingTeacher = new Teacher();
        existingTeacher.setTid(teacherId);
        existingTeacher.setName("Old Teacher");
        existingTeacher.setDepartments(new HashSet<>());

        when(teacherRepo.findById(teacherId)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepo.save(any(Teacher.class))).thenReturn(existingTeacher);

        Responsedto<Teacher> response = teacherService.update(teacherId, teacherRequestDTO);

        assertTrue(response.getSuccess());
        assertEquals("Updated Successfully", response.getMessage());
        assertEquals(existingTeacher, response.getResult());
    }

    @Test
    public void testDeleteTeacher() {
        long teacherId = 1L;

        Responsedto<Teacher> response = teacherService.delete(teacherId);

        assertNull(response.getResult());
        assertTrue(response.getSuccess());
    }
}
