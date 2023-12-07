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

    private String name;
    private String phno;
    private Set<Department> department;
}
