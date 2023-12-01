package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface   TeacherRepo extends JpaRepository<Teacher,Long> {
}
