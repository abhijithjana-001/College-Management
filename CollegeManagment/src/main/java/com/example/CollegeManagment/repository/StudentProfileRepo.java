package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.StudentProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentProfileRepo extends JpaRepository<StudentProfileImg,Long> {
}
