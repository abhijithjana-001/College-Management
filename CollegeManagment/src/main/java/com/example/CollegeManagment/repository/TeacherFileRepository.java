package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.StudentProfileImg;
import com.example.CollegeManagment.entity.TeacherProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherFileRepository extends JpaRepository<TeacherProfileImg,Long> {
    Optional<TeacherProfileImg> findByName(String name);
    boolean existsByName(String name);
}