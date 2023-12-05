package com.example.CollegeManagment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="teacher")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tid")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tid;
    private String name;
    @ManyToMany(mappedBy = "teachers")

    private Set<Department> department;

//    @Override
//    public int hashCode() {
//        return Objects.hash(tid);
//    }



}
