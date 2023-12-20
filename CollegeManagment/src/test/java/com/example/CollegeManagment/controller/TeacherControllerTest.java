//package com.example.CollegeManagment.controller;
//
//import com.example.CollegeManagment.dto.requestdto.TeacherRequestDTO;
//import com.example.CollegeManagment.dto.responsedto.Responsedto;
//import com.example.CollegeManagment.entity.Teacher;
//import com.example.CollegeManagment.service.Teacherservice;
//import com.example.CollegeManagment.service.impl.TeacherServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(TeacherController.class)
//class TeacherControllerTest{
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private Teacherservice teacherService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void addTeacherTest() throws Exception {
//        TeacherRequestDTO requestDTO = new TeacherRequestDTO();
//        requestDTO.setName("John Doe");
//
//        Responsedto<Teacher> responseDTO = new Responsedto<>(true, "Added Successfully", new Teacher());
//
//        when(teacherService.createorupdate(null,requestDTO)).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/teacher/addTeacher")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("Added Successfully"));
//    }
//
//    @Test
//    void findAllTeachersTest() throws Exception {
//
//        List<Teacher> teachers = Arrays.asList(new Teacher());
//        Responsedto<List<Teacher>> responseDTO = new Responsedto<>(true, "Teacher List", teachers);
//
//        when(teacherService.findAll(5,0,"name")).thenReturn(responseDTO);
//
//        this.mockMvc.perform(get("/teacher/teachers")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("Teacher List"));
//    }
//
//    @Test
//    void updateTeacherTest() throws Exception {
//        long teacherId = 1L;
//        TeacherRequestDTO requestDTO = new TeacherRequestDTO();
//        requestDTO.setName("Updated Teacher");
//
//        Responsedto<Teacher> responseDTO = new Responsedto<>(true, "Updated Successfully", new Teacher());
//
//        when(teacherService.createorupdate(teacherId, requestDTO)).thenReturn(responseDTO);
//
//        mockMvc.perform(put("/teacher/update/{id}", teacherId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("Updated Successfully"));
//    }
//
//    @Test
//    void deleteTeacherTest() throws Exception {
//        long teacherId = 1L;
//        Responsedto<Teacher> responseDTO = new Responsedto<>(true, "Deleted Successfully", null);
//
//        when(teacherService.delete(teacherId)).thenReturn(responseDTO);
//
//        mockMvc.perform(delete("/teacher/delete/{id}", teacherId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("Deleted Successfully"));
//        verify(teacherService, times(1)).delete(teacherId);
//    }
//
////    private static String asJsonString(final Object obj) {
////        try {
////            return new ObjectMapper().writeValueAsString(obj);
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////    }
//}
