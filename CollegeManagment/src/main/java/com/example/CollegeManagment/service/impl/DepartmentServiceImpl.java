package com.example.CollegeManagment.service.impl;

import com.example.CollegeManagment.Exception.BadRequest;
import com.example.CollegeManagment.Exception.ItemNotFound;
import com.example.CollegeManagment.config.DepartmentMapper;
import com.example.CollegeManagment.dto.requestdto.DepartmentDto;
import com.example.CollegeManagment.dto.responsedto.Responsedto;
import com.example.CollegeManagment.entity.Department;
import com.example.CollegeManagment.entity.DepartmentFileEntity;
import com.example.CollegeManagment.repository.DepartmentFileRepository;
import com.example.CollegeManagment.repository.DepartmentRepo;
import com.example.CollegeManagment.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentFileRepository departmentFileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentFileService departmentService;

@Override
public Responsedto<Department> createOrUpdate(String departmentDto, MultipartFile file, Long id) {

    DepartmentDto departmentdto = null;

    try {
        departmentdto = objectMapper.readValue(departmentDto, DepartmentDto.class);
    } catch (JsonProcessingException e){
        throw new RuntimeException(e);
    }

    Department department = departmentMapper.toEntity(departmentDto);
    DepartmentFileEntity departmentFileEntity = departmentFileRepository.upload(file);


    if (id == null) {
        department.setId(new Department().getId());
        department.setDepartmentImg(departmentFileEntity);
    } else {
        if(departmentFileRepository.findById(id).isPresent()){
            department.setId(id);
            departmentService.deleteFile(departmentRepo.findById(id).get().getDepartmentImg().getName());
            department.setDepartmentImg(departmentFileEntity);
        } else
            throw new ItemNotFound("Department with id "+ id +" is not found");
    }

    if (!departmentRepo.existsByName(departmentdto.getName())) {
         departmentRepo.save(department);
    } else
        throw new BadRequest("Department name already exist");

    return new Responsedto<>(true, "Department added", department);
}


    @Override
    public Responsedto<List<Department>> findAllDepartments(Integer pageSize, Integer pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Department> pageDepartment = departmentRepo.findAll(pageable);
        List<Department> departments = pageDepartment.getContent();

        return new Responsedto<>(true, "Department List", departments);
    }

    @Override
    public Responsedto<Department> delete(Long id) {
        departmentRepo.deleteById(id);
        return new Responsedto<>(true, "Successfully Deleted", null);
    }

}
