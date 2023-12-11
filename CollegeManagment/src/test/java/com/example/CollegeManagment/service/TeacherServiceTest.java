package com.example.CollegeManagment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.TeacherMapStruct;
import com.example.CollegeManagment.dto.requestdto.Studentdto;
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
import org.mockito.Mockito;
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

    @Mock
    private TeacherMapStruct teacherMapStruct;


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
    public void testAddorUpdateTeacher() {
        long teacherId = 1L;
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("TeacherOne");
        teacherRequestDTO.setPhno("8075505822");
        Department department = new Department();
        department.setId(1L);
        department.setName("DepartmentOne");
        HashSet<Department> set=new HashSet();
        set.add(department);

        teacherRequestDTO.setDepartment((Set<Department>) set);

        Teacher existingTeacher = new Teacher();
        existingTeacher.setTid(teacherId);
        existingTeacher.setName("TeacherTwo");
        existingTeacher.setPhno("9446920711");
        existingTeacher.setDepartments(new HashSet<>());

        when(teacherRepo.findById(teacherId)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepo.save(any(Teacher.class))).thenReturn(existingTeacher);

        Responsedto<Teacher> response = teacherService.createorupdate(teacherId, teacherRequestDTO);

        assertTrue(response.getSuccess());
        assertEquals("Updated Successfully", response.getMessage());
        assertEquals(existingTeacher, response.getResult());
    }

    @Test
    void createOrUpdate_NewTeacher() {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("New Teacher");
        teacherRequestDTO.setPhno("1234567890");
        teacherRequestDTO.setDepartment(new HashSet<>());

        when(teacherRepo.existsByPhno(any())).thenReturn(false);

        Responsedto response = teacherService.createorupdate(null, teacherRequestDTO);

        assertTrue(response.getSuccess());
        assertEquals("Updated Successfully", response.getMessage());
        assertNotNull(response.getResult());
    }

    @Test
    void createOrUpdate_ThrowException() {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Existing Teacher");
        teacherRequestDTO.setPhno("1234567890");
        teacherRequestDTO.setDepartment(new HashSet<>());

        Teacher existingTeacher = new Teacher();
        existingTeacher.setTid(1L);
        existingTeacher.setName("Existing Teacher");
        existingTeacher.setPhno("1234567890");

        when(teacherRepo.existsByPhno(any())).thenReturn(true);
        when(teacherRepo.findByPhno(any())).thenReturn(Optional.of(new Teacher()));
        when(teacherMapStruct.toEntity(Mockito.any(TeacherRequestDTO.class))).thenReturn(existingTeacher);

        ItemNotFound exception = assertThrows(ItemNotFound.class, () -> {
            teacherService.createorupdate(1L, teacherRequestDTO);
        });

        assertEquals("Teacher not found with ID : 1", exception.getMessage());

        BadRequest badException = assertThrows(BadRequest.class, () -> {
            teacherService.createorupdate(null, teacherRequestDTO);
        });

        assertEquals("Phone number already exists",badException.getMessage());
    }

    @Test
    void createOrUpdate_UpdateExistingTeacher() {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setPhno("9876543210");
        teacherRequestDTO.setName("Updated Teacher");
        teacherRequestDTO.setDepartment(new HashSet<>());

        Teacher existingTeacher = new Teacher();
        existingTeacher.setTid(1L);
        existingTeacher.setName("Existing Teacher");
        existingTeacher.setPhno("1234567890");

        when(teacherRepo.existsByPhno(any())).thenReturn(false);
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(existingTeacher));

        Responsedto response = teacherService.createorupdate(1L, teacherRequestDTO);

        assertTrue(response.getSuccess());
        assertEquals("Updated Successfully", response.getMessage());
        assertNotNull(response.getResult());
    }


//    @Test
//    void createOrUpdate_ThrowBadRequestException() {
//        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
//        teacherRequestDTO.setName("Existing Teacher");
//        teacherRequestDTO.setPhno("1234567890");
//        teacherRequestDTO.setDepartment(new HashSet<>());
//
//        Teacher existingTeacher = new Teacher();
//        existingTeacher.setTid(1L);
//        existingTeacher.setName("Existing Teacher");
//        existingTeacher.setPhno("1234567890");
//
//        // Adjust the mock setup to return the existing teacher when findById is called
//        when(teacherRepo.existsByPhno(any())).thenReturn(true);
//        when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(existingTeacher));
//
//        BadRequest exception = assertThrows(BadRequest.class, () -> {
//            teacherService.createorupdate(1L, teacherRequestDTO);
//        });
//
//        assertEquals("Phone number already exists : ", exception.getMessage());
//    }

    @Test
    public void testDeleteTeacher() {
        long teacherId = 1L;

        Responsedto<Teacher> response = teacherService.delete(teacherId);

        assertNull(response.getResult());
        assertTrue(response.getSuccess());
    }
}
