package com.example.CollegeManagment.dto.requestdto;

import com.example.CollegeManagment.entity.Department;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Studentdto {

    private String sname;
    private Department department;


    @Size(min = 10,max = 12)
    private String phoneNum;
}
