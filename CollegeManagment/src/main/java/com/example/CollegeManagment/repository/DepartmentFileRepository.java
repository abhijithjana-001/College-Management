package com.example.CollegeManagment.repository;


import com.example.CollegeManagment.entity.DepartmentFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepartmentFileRepository extends JpaRepository<DepartmentFileEntity, Long> {

    Optional<DepartmentFileEntity> findByName(String name);

    boolean existsByName(String originalFilename);
}
