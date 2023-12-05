package com.example.CollegeManagment.dto.requestdto;


import com.example.CollegeManagment.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequestDTO {
    private Long id;
    private String name;
    private Set<Department> department;
}
