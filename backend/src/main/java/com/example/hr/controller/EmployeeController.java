package com.example.hr.controller;


import com.example.hr.dto.EmployeeDto;
import com.example.hr.dto.EmployeeResponseDto;
import com.example.hr.service.EmployeeService;
import com.example.hr.service.EmployeeXmlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeXmlService employeeXmlService;


    @GetMapping
    @Operation(summary = "Get all employees")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(
            @PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping("/search")
    @Operation(summary = "Search employees by name")
    public ResponseEntity<List<EmployeeResponseDto>> searchEmployeesByName(
            @RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(
                employeeService.createEmployee(employeeDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    @Operation(summary = "Update an existing employee")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable String employeeId,
            @Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeDto));
    }

    @DeleteMapping("/{employeeId}")
    @Operation(summary = "Delete an employee")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    @Operation(summary = "Delete all employees")
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    @Operation(summary = "Import employees from XML file")
    public ResponseEntity<String> importEmployeesFromXml(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("employees", ".xml");
            file.transferTo(tempFile);

            // Use the importFromXml method with the temp file path
            employeeXmlService.importFromXmlClasspath();

           // employeeXmlService.importFromXml(tempFile.getAbsolutePath());

            return ResponseEntity.ok("Employees imported successfully from XML.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import employees: " + e.getMessage());
        }
    }


}
