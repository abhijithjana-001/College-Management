package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.entity.StudentProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentFileRepository extends JpaRepository<DepartmentFileEntity, Long> {

    Optional<DepartmentFileEntity> findByName(String name);
}
