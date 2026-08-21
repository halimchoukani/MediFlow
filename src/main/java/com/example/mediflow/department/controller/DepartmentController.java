package com.example.mediflow.department.controller;

import com.example.mediflow.department.dto.CreateDepartmentRequest;
import com.example.mediflow.department.dto.DepartmentResponse;
import com.example.mediflow.department.dto.UpdateDepartmentRequest;
import com.example.mediflow.department.entity.Department;
import com.example.mediflow.department.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponse> addDepartment(@Valid @RequestBody CreateDepartmentRequest department) {
        DepartmentResponse departmentResponse = departmentService.createDepartment(department);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentResponse);

    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable UUID departmentId,
            @Valid @RequestBody UpdateDepartmentRequest request
    ) {
        DepartmentResponse response =
                departmentService.updateDepartment(departmentId, request);

        return ResponseEntity.ok(response);
    }

}
