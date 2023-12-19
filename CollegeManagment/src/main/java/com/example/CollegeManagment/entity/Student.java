package com.example.CollegeManagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="student")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;
    private String  sname;

    private String phoneNum;



    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private StudentProfileImg profileImg;
}
