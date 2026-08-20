package com.example.mediflow.department.repository;


import com.example.mediflow.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
