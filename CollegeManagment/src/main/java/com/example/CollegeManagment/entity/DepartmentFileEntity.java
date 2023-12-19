package com.example.CollegeManagment.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Image_table")
public class DepartmentFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private LocalDateTime created;

    private Long size;

    private String filePath;

    @OneToOne(mappedBy = "departmentImg",cascade = CascadeType.ALL)
    private  Department department;

}
