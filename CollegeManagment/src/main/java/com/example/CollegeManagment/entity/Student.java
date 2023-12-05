package com.example.CollegeManagment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long student_id;
    private String  sname;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name="id",nullable = false)
    private Department department;
}
