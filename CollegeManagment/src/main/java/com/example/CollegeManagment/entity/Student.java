package com.example.CollegeManagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="student")
public class Student {
    @Id
    private Long student_id;
    private String  name;

    @OneToMany(cascade = CascadeType.ALL)
    private Department department;
}
