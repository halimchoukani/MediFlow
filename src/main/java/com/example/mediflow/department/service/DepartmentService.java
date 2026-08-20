package com.example.mediflow.department.service;

import com.example.mediflow.common.exception.DuplicateResourceException;
import com.example.mediflow.department.dto.CreateDepartmentRequest;
import com.example.mediflow.department.dto.DepartmentResponse;
import com.example.mediflow.department.entity.Department;
import com.example.mediflow.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentResponse createDepartment(
            CreateDepartmentRequest request
    ) {

        if (departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException(
                    "Department already exists: " + request.name()
            );
        }

        Department department = new Department();

        department.setName(request.name());
        department.setDescription(request.description());

        Department savedDepartment =
                departmentRepository.save(department);

        return new DepartmentResponse(
                savedDepartment.getId(),
                savedDepartment.getName(),
                savedDepartment.getDescription(),
                savedDepartment.isActive(),
                savedDepartment.getCreatedAt(),
                savedDepartment.getUpdatedAt()
        );
    }
}