package com.example.CollegeManagment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<Student> students;

    @JsonIgnore
    @ManyToMany(mappedBy = "departments")
    private Set<Teacher> teachers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imgId",referencedColumnName = "id")
    private DepartmentFileEntity departmentImg;

    public Department(Long id, String name){
        this.id = id;
        this.name=name;
    }





}
