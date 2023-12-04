package com.example.CollegeManagment.dto.requestdto;

import com.example.CollegeManagment.entity.Department;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class studentdto {
    private String  name;
    private Department department;
}
