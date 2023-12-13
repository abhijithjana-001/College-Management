package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.TeacherMapStruct;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TeacherServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TeacherServiceImplDiffblueTest {
    @MockBean
    private DepartmentRepo departmentRepo;

    @MockBean
    private TeacherMapStruct teacherMapStruct;

    @MockBean
    private TeacherRepo teacherRepo;

    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    /**
     * Method under test:
     * {@link TeacherServiceImpl#findAll(Integer, Integer, String)}
     */
    @Test
    void testFindAll() {
        when(teacherRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Responsedto<List<Teacher>> actualFindAllResult = teacherServiceImpl.findAll(3, 10, "Sort");
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
        assertEquals("Teachers List", actualFindAllResult.getMessage());
        assertTrue(actualFindAllResult.getSuccess());
        assertTrue(actualFindAllResult.getResult().isEmpty());
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#findAll(Integer, Integer, String)}
     */
    @Test
    void testFindAll2() {
        when(teacherRepo.findAll(Mockito.<Pageable>any())).thenThrow(new ItemNotFound("Msg"));
        assertThrows(ItemNotFound.class, () -> teacherServiceImpl.findAll(3, 10, "Sort"));
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#createorupdate(Long, TeacherRequestDTO)}
     */
    @Test
    void testCreateorupdate() {
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTid(1L);

        Teacher teacher3 = new Teacher();
        teacher3.setDepartments(new HashSet<>());
        teacher3.setName("Name");
        teacher3.setPhno("Phno");
        teacher3.setTid(1L);
        Optional<Teacher> ofResult = Optional.of(teacher3);
        when(teacherRepo.findByPhno(Mockito.<String>any())).thenReturn(ofResult);
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
        when(teacherRepo.save(Mockito.<Teacher>any())).thenReturn(teacher2);
        when(teacherRepo.existsById(Mockito.<Long>any())).thenReturn(true);
        Responsedto<Teacher> actualCreateorupdateResult = teacherServiceImpl.createorupdate(1L, new TeacherRequestDTO());
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).findByPhno(Mockito.<String>any());
        verify(teacherRepo).existsById(Mockito.<Long>any());
        verify(teacherRepo).save(Mockito.<Teacher>any());
        assertEquals("Updated Successfully", actualCreateorupdateResult.getMessage());
        Teacher result = actualCreateorupdateResult.getResult();
        assertEquals(1L, result.getTid());
        assertTrue(actualCreateorupdateResult.getSuccess());
        assertSame(teacher, result);
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#createorupdate(Long, TeacherRequestDTO)}
     */
    @Test
    void testCreateorupdate2() {
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);
        when(teacherRepo.findByPhno(Mockito.<String>any())).thenThrow(new ItemNotFound("Msg"));
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
        when(teacherRepo.existsById(Mockito.<Long>any())).thenReturn(true);
        assertThrows(ItemNotFound.class, () -> teacherServiceImpl.createorupdate(1L, new TeacherRequestDTO()));
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).findByPhno(Mockito.<String>any());
        verify(teacherRepo).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#createorupdate(Long, TeacherRequestDTO)}
     */
    @Test
    void testCreateorupdate3() {
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTid(2L);
        Optional<Teacher> ofResult = Optional.of(teacher2);
        when(teacherRepo.findByPhno(Mockito.<String>any())).thenReturn(ofResult);
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
        when(teacherRepo.existsById(Mockito.<Long>any())).thenReturn(true);
        assertThrows(BadRequest.class, () -> teacherServiceImpl.createorupdate(1L, new TeacherRequestDTO()));
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).findByPhno(Mockito.<String>any());
        verify(teacherRepo).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#createorupdate(Long, TeacherRequestDTO)}
     */
    @Test
    void testCreateorupdate4() {
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTid(1L);
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(false);
        when(teacherRepo.save(Mockito.<Teacher>any())).thenReturn(teacher2);
        when(teacherRepo.existsById(Mockito.<Long>any())).thenReturn(true);
        Responsedto<Teacher> actualCreateorupdateResult = teacherServiceImpl.createorupdate(1L, new TeacherRequestDTO());
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).existsById(Mockito.<Long>any());
        verify(teacherRepo).save(Mockito.<Teacher>any());
        assertEquals("Updated Successfully", actualCreateorupdateResult.getMessage());
        Teacher result = actualCreateorupdateResult.getResult();
        assertEquals(1L, result.getTid());
        assertTrue(actualCreateorupdateResult.getSuccess());
        assertSame(teacher, result);
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#createorupdate(Long, TeacherRequestDTO)}
     */
    @Test
    void testCreateorupdate5() {
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);
        when(teacherRepo.existsById(Mockito.<Long>any())).thenReturn(false);
        assertThrows(ItemNotFound.class, () -> teacherServiceImpl.createorupdate(1L, new TeacherRequestDTO()));
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link TeacherServiceImpl#createorupdate(Long, TeacherRequestDTO)}
     */
    @Test
    void testCreateorupdate6() {
        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTid(1L);
        Optional<Teacher> ofResult = Optional.of(teacher2);
        when(teacherRepo.findByPhno(Mockito.<String>any())).thenReturn(ofResult);
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
        assertThrows(BadRequest.class, () -> teacherServiceImpl.createorupdate(null, new TeacherRequestDTO()));
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).findByPhno(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TeacherServiceImpl#delete(long)}
     */
    @Test
    void testDelete() {
        doNothing().when(teacherRepo).deleteById(Mockito.<Long>any());
        Responsedto<Teacher> actualDeleteResult = teacherServiceImpl.delete(1L);
        verify(teacherRepo).deleteById(Mockito.<Long>any());
        assertEquals("Deleted Successfully", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }

    /**
     * Method under test: {@link TeacherServiceImpl#delete(long)}
     */
    @Test
    void testDelete2() {
        doThrow(new ItemNotFound("Deleted Successfully")).when(teacherRepo).deleteById(Mockito.<Long>any());
        assertThrows(ItemNotFound.class, () -> teacherServiceImpl.delete(1L));
        verify(teacherRepo).deleteById(Mockito.<Long>any());
    }
}
