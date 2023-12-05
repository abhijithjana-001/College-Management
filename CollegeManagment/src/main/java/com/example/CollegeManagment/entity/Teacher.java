package com.example.CollegeManagment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="teacher")

public class Teacher {
    @Id

    private long tid;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "teacher_department", // Specify the name of the join table
            joinColumns = @JoinColumn(name = "teacher_id"), // Specify the column in the join table for Teacher
            inverseJoinColumns = @JoinColumn(name = "department_id") // Specify the column in the join table for Department
    )
    private Set<Department> departments=new HashSet<>();
}