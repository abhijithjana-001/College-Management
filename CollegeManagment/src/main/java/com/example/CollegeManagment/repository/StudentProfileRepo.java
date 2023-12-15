package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.StudentProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentProfileRepo extends JpaRepository<StudentProfileImg,Long> {

    Optional<StudentProfileImg> findByName(String name);
}
