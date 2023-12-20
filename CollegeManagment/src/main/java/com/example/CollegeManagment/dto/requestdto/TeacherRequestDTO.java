package com.example.CollegeManagment.dto.requestdto;


import com.example.CollegeManagment.entity.Department;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequestDTO {

    private String name;

    @Size(min = 10,max = 12)
    private String phno;

    private Set<Department> department;

}
