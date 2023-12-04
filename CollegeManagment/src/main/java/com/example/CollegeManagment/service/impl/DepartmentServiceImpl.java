package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Responsedto addDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        departmentRepo.save(department);
        return new Responsedto(true, "Department added", department);
    }

    @Override
    public Responsedto<List<Department>> findAllDepartments() {
        List<Department> departments = departmentRepo.findAll();
        return new Responsedto<>(true, "Department List", departments);
    }

    @Override
    public Responsedto<Department> updateDepartment(long id, DepartmentDto departmentDto) {
        Department department = departmentRepo.findById(id).orElseThrow(()->new
                ItemNotFound("Department not found with ID : "+id));

        department.setName(department.getName());
        departmentRepo.save(department);
        return new Responsedto<Department>(true, "Updated Successfully", department);
    }

    @Override
    public Responsedto<Department> delete(Long id) {
        departmentRepo.deleteById(id);
        return null;
    }



}
