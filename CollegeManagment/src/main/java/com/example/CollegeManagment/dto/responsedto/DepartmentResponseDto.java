package com.example.CollegeManagment.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDto<T> {

    private Integer id;

    private String name;

    private T data;

}
