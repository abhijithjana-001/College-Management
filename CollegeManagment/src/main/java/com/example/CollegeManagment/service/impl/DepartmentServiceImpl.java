package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.DepartmentMapper;
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
    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public Responsedto createOrUpdate(DepartmentDto departmentDto, Long id){
        Department existingDepartment = departmentRepo.findByNameIgnoreCase(departmentDto.getName());

        if (existingDepartment != null) {
            throw new BadRequest("Department name already exists");
        }

        Department department;

        if (id == null) {
            department = new Department();
        }else {
            department = departmentRepo.findById(id)
                    .orElseThrow(() -> new ItemNotFound("Department not found with ID: " + id));
        }

        department = departmentMapper.toEntity(departmentDto);
        departmentRepo.save(department);

        return new Responsedto<>(true, "Department added", department);
    }

    @Override
    public Responsedto<List<Department>> findAllDepartments() {
        List<Department> departments = departmentRepo.findAll();
        return new Responsedto<>(true, "Department List", departments);
    }

    @Override
    public Responsedto<Department> delete(Long id) {
        departmentRepo.deleteById(id);
        return new Responsedto<>(true, "Successfully Deleted", null);
    }

}
