package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  StudentRepo extends JpaRepository<Student,Long> {
}
