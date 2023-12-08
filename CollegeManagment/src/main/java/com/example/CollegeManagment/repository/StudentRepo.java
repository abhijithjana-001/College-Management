package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  StudentRepo extends JpaRepository<Student,Long> {

    Optional<Student> findByPhoneNum(String phone_num);


    boolean existsByPhoneNum(String phone_num);
}

