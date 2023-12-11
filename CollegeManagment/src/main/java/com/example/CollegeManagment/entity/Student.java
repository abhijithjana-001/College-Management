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

@Table(name="student")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "student_id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long student_id;
    private String  sname;

    private String phoneNum;



    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

}
