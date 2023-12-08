package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface   TeacherRepo extends JpaRepository<Teacher,Long> {


    Optional<Teacher> findByPhno(String phno);

    boolean existsByPhno(String phno);
}
