package com.example.CollegeManagment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="student")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "student_id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long student_id;
    private String  sname;
    //    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL )
//   @JoinColumn(name="id",nullable = false)


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;


//    @Override
//    public int hashCode() {
//        return Objects.hash(student_id);
//    }

}
