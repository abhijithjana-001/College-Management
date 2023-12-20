package com.example.CollegeManagment.repository;

import com.example.CollegeManagment.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    Department findByNameIgnoreCase(String name);

    boolean existsByName(String name);
}
