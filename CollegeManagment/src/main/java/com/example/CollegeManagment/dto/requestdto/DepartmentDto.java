package com.example.CollegeManagment.dto.requestdto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {

    private Long Id;

    @NotEmpty
    @Size(min = 8, message = "department name should have at least 2 characters")
    private String name;

}