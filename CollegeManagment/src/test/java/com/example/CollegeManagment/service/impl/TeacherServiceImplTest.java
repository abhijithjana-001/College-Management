package com.example.CollegeManagment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CollegeManagment.config.TeacherMapStruct;
import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Teacher;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.repository.TeacherRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {TeacherServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TeacherServiceImplTest {
    @MockBean
    private DepartmentRepo departmentRepo;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherFileService teacherFileService;

    @MockBean
    private TeacherMapStruct teacherMapStruct;

    @MockBean
    private TeacherRepo teacherRepo;

    @Autowired
    private TeacherServiceImpl teacherServiceImpl;


    @Test
    void testCreateorupdate() throws IOException {
        TeacherRequestDTO.TeacherRequestDTOBuilder builderResult = TeacherRequestDTO.builder();
        TeacherRequestDTO buildResult = builderResult.department(new HashSet<>()).name("Name").phno("Phno").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any()))
                .thenReturn(buildResult);

        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();
        teacherProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg.setFilePath("/directory/foo.txt");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("Name");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setTeacher(new Teacher());
        teacherProfileImg.setType("Type");

        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTeacherProfileImg(teacherProfileImg);
        teacher.setTid(1L);

        TeacherProfileImg teacherProfileImg2 = new TeacherProfileImg();
        teacherProfileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg2.setFilePath("/directory/foo.txt");
        teacherProfileImg2.setId(1L);
        teacherProfileImg2.setName("Name");
        teacherProfileImg2.setSize(3L);
        teacherProfileImg2.setTeacher(teacher);
        teacherProfileImg2.setType("Type");

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTeacherProfileImg(teacherProfileImg2);
        teacher2.setTid(1L);

        TeacherProfileImg teacherProfileImg3 = new TeacherProfileImg();
        teacherProfileImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg3.setFilePath("/directory/foo.txt");
        teacherProfileImg3.setId(1L);
        teacherProfileImg3.setName("Name");
        teacherProfileImg3.setSize(3L);
        teacherProfileImg3.setTeacher(teacher2);
        teacherProfileImg3.setType("Type");
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenReturn(new Responsedto());
        when(teacherFileService.upload(Mockito.<MultipartFile>any())).thenReturn(teacherProfileImg3);

        Teacher teacher3 = new Teacher();
        teacher3.setDepartments(new HashSet<>());
        teacher3.setName("Name");
        teacher3.setPhno("Phno");
        teacher3.setTeacherProfileImg(new TeacherProfileImg());
        teacher3.setTid(1L);

        TeacherProfileImg teacherProfileImg4 = new TeacherProfileImg();
        teacherProfileImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg4.setFilePath("/directory/foo.txt");
        teacherProfileImg4.setId(1L);
        teacherProfileImg4.setName("Name");
        teacherProfileImg4.setSize(3L);
        teacherProfileImg4.setTeacher(teacher3);
        teacherProfileImg4.setType("Type");

        Teacher teacher4 = new Teacher();
        teacher4.setDepartments(new HashSet<>());
        teacher4.setName("Name");
        teacher4.setPhno("Phno");
        teacher4.setTeacherProfileImg(teacherProfileImg4);
        teacher4.setTid(1L);

        TeacherProfileImg teacherProfileImg5 = new TeacherProfileImg();
        teacherProfileImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg5.setFilePath("/directory/foo.txt");
        teacherProfileImg5.setId(1L);
        teacherProfileImg5.setName("Name");
        teacherProfileImg5.setSize(3L);
        teacherProfileImg5.setTeacher(teacher4);
        teacherProfileImg5.setType("Type");

        Teacher teacher5 = new Teacher();
        teacher5.setDepartments(new HashSet<>());
        teacher5.setName("Name");
        teacher5.setPhno("Phno");
        teacher5.setTeacherProfileImg(teacherProfileImg5);
        teacher5.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher5);

        TeacherProfileImg teacherProfileImg6 = new TeacherProfileImg();
        teacherProfileImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg6.setFilePath("/directory/foo.txt");
        teacherProfileImg6.setId(1L);
        teacherProfileImg6.setName("Name");
        teacherProfileImg6.setSize(3L);
        teacherProfileImg6.setTeacher(new Teacher());
        teacherProfileImg6.setType("Type");

        Teacher teacher6 = new Teacher();
        teacher6.setDepartments(new HashSet<>());
        teacher6.setName("Name");
        teacher6.setPhno("Phno");
        teacher6.setTeacherProfileImg(teacherProfileImg6);
        teacher6.setTid(1L);

        TeacherProfileImg teacherProfileImg7 = new TeacherProfileImg();
        teacherProfileImg7.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg7.setFilePath("/directory/foo.txt");
        teacherProfileImg7.setId(1L);
        teacherProfileImg7.setName("Name");
        teacherProfileImg7.setSize(3L);
        teacherProfileImg7.setTeacher(teacher6);
        teacherProfileImg7.setType("Type");

        Teacher teacher7 = new Teacher();
        teacher7.setDepartments(new HashSet<>());
        teacher7.setName("Name");
        teacher7.setPhno("Phno");
        teacher7.setTeacherProfileImg(teacherProfileImg7);
        teacher7.setTid(1L);
        Optional<Teacher> ofResult = Optional.of(teacher7);

        Teacher teacher8 = new Teacher();
        teacher8.setDepartments(new HashSet<>());
        teacher8.setName("Name");
        teacher8.setPhno("Phno");
        teacher8.setTeacherProfileImg(new TeacherProfileImg());
        teacher8.setTid(1L);

        TeacherProfileImg teacherProfileImg8 = new TeacherProfileImg();
        teacherProfileImg8.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg8.setFilePath("/directory/foo.txt");
        teacherProfileImg8.setId(1L);
        teacherProfileImg8.setName("Name");
        teacherProfileImg8.setSize(3L);
        teacherProfileImg8.setTeacher(teacher8);
        teacherProfileImg8.setType("Type");

        Teacher teacher9 = new Teacher();
        teacher9.setDepartments(new HashSet<>());
        teacher9.setName("Name");
        teacher9.setPhno("Phno");
        teacher9.setTeacherProfileImg(teacherProfileImg8);
        teacher9.setTid(1L);

        TeacherProfileImg teacherProfileImg9 = new TeacherProfileImg();
        teacherProfileImg9.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg9.setFilePath("/directory/foo.txt");
        teacherProfileImg9.setId(1L);
        teacherProfileImg9.setName("Name");
        teacherProfileImg9.setSize(3L);
        teacherProfileImg9.setTeacher(teacher9);
        teacherProfileImg9.setType("Type");

        Teacher teacher10 = new Teacher();
        teacher10.setDepartments(new HashSet<>());
        teacher10.setName("Name");
        teacher10.setPhno("Phno");
        teacher10.setTeacherProfileImg(teacherProfileImg9);
        teacher10.setTid(1L);

        TeacherProfileImg teacherProfileImg10 = new TeacherProfileImg();
        teacherProfileImg10.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg10.setFilePath("/directory/foo.txt");
        teacherProfileImg10.setId(1L);
        teacherProfileImg10.setName("Name");
        teacherProfileImg10.setSize(3L);
        teacherProfileImg10.setTeacher(new Teacher());
        teacherProfileImg10.setType("Type");

        Teacher teacher11 = new Teacher();
        teacher11.setDepartments(new HashSet<>());
        teacher11.setName("Name");
        teacher11.setPhno("Phno");
        teacher11.setTeacherProfileImg(teacherProfileImg10);
        teacher11.setTid(1L);

        TeacherProfileImg teacherProfileImg11 = new TeacherProfileImg();
        teacherProfileImg11.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg11.setFilePath("/directory/foo.txt");
        teacherProfileImg11.setId(1L);
        teacherProfileImg11.setName("Name");
        teacherProfileImg11.setSize(3L);
        teacherProfileImg11.setTeacher(teacher11);
        teacherProfileImg11.setType("Type");

        Teacher teacher12 = new Teacher();
        teacher12.setDepartments(new HashSet<>());
        teacher12.setName("Name");
        teacher12.setPhno("Phno");
        teacher12.setTeacherProfileImg(teacherProfileImg11);
        teacher12.setTid(1L);
        Optional<Teacher> ofResult2 = Optional.of(teacher12);
        when(teacherRepo.findByPhno(Mockito.<String>any())).thenReturn(ofResult2);
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
        when(teacherRepo.save(Mockito.<Teacher>any())).thenReturn(teacher10);
        when(teacherRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Responsedto<Teacher> actualCreateorupdateResult = teacherServiceImpl.createorupdate(1L, "Teacherdto Data",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).findByPhno(Mockito.<String>any());
        verify(teacherFileService).deleteFile(Mockito.<String>any());
        verify(teacherFileService).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any());
        verify(teacherRepo, atLeast(1)).findById(Mockito.<Long>any());
        verify(teacherRepo).save(Mockito.<Teacher>any());
        assertEquals("Updated Successfully", actualCreateorupdateResult.getMessage());
        Teacher result = actualCreateorupdateResult.getResult();
        assertEquals(1L, result.getTid());
        assertTrue(actualCreateorupdateResult.getSuccess());
        assertSame(teacher5, result);
        assertSame(teacherProfileImg3, result.getTeacherProfileImg());
    }


    @Test
    void testCreateorupdate2() throws IOException {
        TeacherRequestDTO.TeacherRequestDTOBuilder builderResult = TeacherRequestDTO.builder();
        TeacherRequestDTO buildResult = builderResult.department(new HashSet<>()).name("Name").phno("Phno").build();
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any()))
                .thenReturn(buildResult);

        TeacherProfileImg teacherProfileImg = new TeacherProfileImg();
        teacherProfileImg.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg.setFilePath("/directory/foo.txt");
        teacherProfileImg.setId(1L);
        teacherProfileImg.setName("Name");
        teacherProfileImg.setSize(3L);
        teacherProfileImg.setTeacher(new Teacher());
        teacherProfileImg.setType("Type");

        Teacher teacher = new Teacher();
        teacher.setDepartments(new HashSet<>());
        teacher.setName("Name");
        teacher.setPhno("Phno");
        teacher.setTeacherProfileImg(teacherProfileImg);
        teacher.setTid(1L);

        TeacherProfileImg teacherProfileImg2 = new TeacherProfileImg();
        teacherProfileImg2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg2.setFilePath("/directory/foo.txt");
        teacherProfileImg2.setId(1L);
        teacherProfileImg2.setName("Name");
        teacherProfileImg2.setSize(3L);
        teacherProfileImg2.setTeacher(teacher);
        teacherProfileImg2.setType("Type");

        Teacher teacher2 = new Teacher();
        teacher2.setDepartments(new HashSet<>());
        teacher2.setName("Name");
        teacher2.setPhno("Phno");
        teacher2.setTeacherProfileImg(teacherProfileImg2);
        teacher2.setTid(1L);

        TeacherProfileImg teacherProfileImg3 = new TeacherProfileImg();
        teacherProfileImg3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg3.setFilePath("/directory/foo.txt");
        teacherProfileImg3.setId(1L);
        teacherProfileImg3.setName("Name");
        teacherProfileImg3.setSize(3L);
        teacherProfileImg3.setTeacher(teacher2);
        teacherProfileImg3.setType("Type");
        when(teacherFileService.deleteFile(Mockito.<String>any())).thenThrow(new RuntimeException("Updated Successfully"));
        when(teacherFileService.upload(Mockito.<MultipartFile>any())).thenReturn(teacherProfileImg3);

        Teacher teacher3 = new Teacher();
        teacher3.setDepartments(new HashSet<>());
        teacher3.setName("Name");
        teacher3.setPhno("Phno");
        teacher3.setTeacherProfileImg(new TeacherProfileImg());
        teacher3.setTid(1L);

        TeacherProfileImg teacherProfileImg4 = new TeacherProfileImg();
        teacherProfileImg4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg4.setFilePath("/directory/foo.txt");
        teacherProfileImg4.setId(1L);
        teacherProfileImg4.setName("Name");
        teacherProfileImg4.setSize(3L);
        teacherProfileImg4.setTeacher(teacher3);
        teacherProfileImg4.setType("Type");

        Teacher teacher4 = new Teacher();
        teacher4.setDepartments(new HashSet<>());
        teacher4.setName("Name");
        teacher4.setPhno("Phno");
        teacher4.setTeacherProfileImg(teacherProfileImg4);
        teacher4.setTid(1L);

        TeacherProfileImg teacherProfileImg5 = new TeacherProfileImg();
        teacherProfileImg5.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg5.setFilePath("/directory/foo.txt");
        teacherProfileImg5.setId(1L);
        teacherProfileImg5.setName("Name");
        teacherProfileImg5.setSize(3L);
        teacherProfileImg5.setTeacher(teacher4);
        teacherProfileImg5.setType("Type");

        Teacher teacher5 = new Teacher();
        teacher5.setDepartments(new HashSet<>());
        teacher5.setName("Name");
        teacher5.setPhno("Phno");
        teacher5.setTeacherProfileImg(teacherProfileImg5);
        teacher5.setTid(1L);
        when(teacherMapStruct.toEntity(Mockito.<TeacherRequestDTO>any())).thenReturn(teacher5);

        TeacherProfileImg teacherProfileImg6 = new TeacherProfileImg();
        teacherProfileImg6.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg6.setFilePath("/directory/foo.txt");
        teacherProfileImg6.setId(1L);
        teacherProfileImg6.setName("Name");
        teacherProfileImg6.setSize(3L);
        teacherProfileImg6.setTeacher(new Teacher());
        teacherProfileImg6.setType("Type");

        Teacher teacher6 = new Teacher();
        teacher6.setDepartments(new HashSet<>());
        teacher6.setName("Name");
        teacher6.setPhno("Phno");
        teacher6.setTeacherProfileImg(teacherProfileImg6);
        teacher6.setTid(1L);

        TeacherProfileImg teacherProfileImg7 = new TeacherProfileImg();
        teacherProfileImg7.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg7.setFilePath("/directory/foo.txt");
        teacherProfileImg7.setId(1L);
        teacherProfileImg7.setName("Name");
        teacherProfileImg7.setSize(3L);
        teacherProfileImg7.setTeacher(teacher6);
        teacherProfileImg7.setType("Type");

        Teacher teacher7 = new Teacher();
        teacher7.setDepartments(new HashSet<>());
        teacher7.setName("Name");
        teacher7.setPhno("Phno");
        teacher7.setTeacherProfileImg(teacherProfileImg7);
        teacher7.setTid(1L);
        Optional<Teacher> ofResult = Optional.of(teacher7);

        Teacher teacher8 = new Teacher();
        teacher8.setDepartments(new HashSet<>());
        teacher8.setName("Name");
        teacher8.setPhno("Phno");
        teacher8.setTeacherProfileImg(new TeacherProfileImg());
        teacher8.setTid(1L);

        TeacherProfileImg teacherProfileImg8 = new TeacherProfileImg();
        teacherProfileImg8.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg8.setFilePath("/directory/foo.txt");
        teacherProfileImg8.setId(1L);
        teacherProfileImg8.setName("Name");
        teacherProfileImg8.setSize(3L);
        teacherProfileImg8.setTeacher(teacher8);
        teacherProfileImg8.setType("Type");

        Teacher teacher9 = new Teacher();
        teacher9.setDepartments(new HashSet<>());
        teacher9.setName("Name");
        teacher9.setPhno("Phno");
        teacher9.setTeacherProfileImg(teacherProfileImg8);
        teacher9.setTid(1L);

        TeacherProfileImg teacherProfileImg9 = new TeacherProfileImg();
        teacherProfileImg9.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg9.setFilePath("/directory/foo.txt");
        teacherProfileImg9.setId(1L);
        teacherProfileImg9.setName("Name");
        teacherProfileImg9.setSize(3L);
        teacherProfileImg9.setTeacher(teacher9);
        teacherProfileImg9.setType("Type");

        Teacher teacher10 = new Teacher();
        teacher10.setDepartments(new HashSet<>());
        teacher10.setName("Name");
        teacher10.setPhno("Phno");
        teacher10.setTeacherProfileImg(teacherProfileImg9);
        teacher10.setTid(1L);

        TeacherProfileImg teacherProfileImg10 = new TeacherProfileImg();
        teacherProfileImg10.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg10.setFilePath("/directory/foo.txt");
        teacherProfileImg10.setId(1L);
        teacherProfileImg10.setName("Name");
        teacherProfileImg10.setSize(3L);
        teacherProfileImg10.setTeacher(new Teacher());
        teacherProfileImg10.setType("Type");

        Teacher teacher11 = new Teacher();
        teacher11.setDepartments(new HashSet<>());
        teacher11.setName("Name");
        teacher11.setPhno("Phno");
        teacher11.setTeacherProfileImg(teacherProfileImg10);
        teacher11.setTid(1L);

        TeacherProfileImg teacherProfileImg11 = new TeacherProfileImg();
        teacherProfileImg11.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        teacherProfileImg11.setFilePath("/directory/foo.txt");
        teacherProfileImg11.setId(1L);
        teacherProfileImg11.setName("Name");
        teacherProfileImg11.setSize(3L);
        teacherProfileImg11.setTeacher(teacher11);
        teacherProfileImg11.setType("Type");

        Teacher teacher12 = new Teacher();
        teacher12.setDepartments(new HashSet<>());
        teacher12.setName("Name");
        teacher12.setPhno("Phno");
        teacher12.setTeacherProfileImg(teacherProfileImg11);
        teacher12.setTid(1L);
        Optional<Teacher> ofResult2 = Optional.of(teacher12);
        when(teacherRepo.findByPhno(Mockito.<String>any())).thenReturn(ofResult2);
        when(teacherRepo.existsByPhno(Mockito.<String>any())).thenReturn(true);
        when(teacherRepo.save(Mockito.<Teacher>any())).thenReturn(teacher10);
        when(teacherRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.createorupdate(1L, "Teacherdto Data",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
        verify(teacherMapStruct).toEntity(Mockito.<TeacherRequestDTO>any());
        verify(teacherRepo).existsByPhno(Mockito.<String>any());
        verify(teacherRepo).findByPhno(Mockito.<String>any());
        verify(teacherFileService).deleteFile(Mockito.<String>any());
        verify(teacherFileService).upload(Mockito.<MultipartFile>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<TeacherRequestDTO>>any());
        verify(teacherRepo, atLeast(1)).findById(Mockito.<Long>any());
        verify(teacherRepo).save(Mockito.<Teacher>any());
    }


    @Test
    void testFindAll() {
        when(teacherRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Responsedto<List<Teacher>> actualFindAllResult = teacherServiceImpl.findAll(3, 10, "Sort");
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
        assertEquals("Teachers List", actualFindAllResult.getMessage());
        assertTrue(actualFindAllResult.getSuccess());
        assertTrue(actualFindAllResult.getResult().isEmpty());
    }


    @Test
    void testFindAll2() {
        when(teacherRepo.findAll(Mockito.<Pageable>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.findAll(3, 10, "Sort"));
        verify(teacherRepo).findAll(Mockito.<Pageable>any());
    }


    @Test
    void testDelete() {
        doNothing().when(teacherRepo).deleteById(Mockito.<Long>any());
        Responsedto actualDeleteResult = teacherServiceImpl.delete(1L);
        verify(teacherRepo).deleteById(Mockito.<Long>any());
        assertEquals("Deleted Successfully", actualDeleteResult.getMessage());
        assertNull(actualDeleteResult.getResult());
        assertTrue(actualDeleteResult.getSuccess());
    }


    @Test
    void testDelete2() {
        doThrow(new RuntimeException("Deleted Successfully")).when(teacherRepo).deleteById(Mockito.<Long>any());
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.delete(1L));
        verify(teacherRepo).deleteById(Mockito.<Long>any());
    }
}
