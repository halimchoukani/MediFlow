package com.example.mediflow.department.service;

import com.example.mediflow.common.exception.DuplicateResourceException;
import com.example.mediflow.department.dto.CreateDepartmentRequest;
import com.example.mediflow.department.dto.DepartmentResponse;
import com.example.mediflow.department.dto.UpdateDepartmentRequest;
import com.example.mediflow.department.entity.Department;
import com.example.mediflow.department.exception.DepartmentNotFoundException;
import com.example.mediflow.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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


    @Transactional
    public DepartmentResponse updateDepartment(
            UUID departmentId,
            UpdateDepartmentRequest request
    ) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id: " + departmentId
                        )
                );

        boolean nameChanged =
                !department.getName().equalsIgnoreCase(request.name());

        if (nameChanged &&
                departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException(
                    "Department already exists: " + request.name()
            );
        }

        department.setName(request.name());
        department.setDescription(request.description());

        Department updatedDepartment =
                departmentRepository.save(department);

        return new DepartmentResponse(
                updatedDepartment.getId(),
                updatedDepartment.getName(),
                updatedDepartment.getDescription(),
                updatedDepartment.isActive(),
                updatedDepartment.getCreatedAt(),
                updatedDepartment.getUpdatedAt()
        );
    }


}