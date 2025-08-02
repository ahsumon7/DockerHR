package com.example.hr.service;





import com.example.hr.dto.EmployeeDto;
import com.example.hr.dto.EmployeeResponseDto;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.model.Employee;
import com.example.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDto getEmployeeById(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        return mapToDto(employee);
    }

    public List<EmployeeResponseDto> searchEmployeesByName(String name) {
        return employeeRepository.searchByName(name)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmployeeId(employeeDto.employeeId())) {
            throw new IllegalArgumentException("Employee with ID " + employeeDto.employeeId() + " already exists");
        }

        Employee employee = mapToEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDto(savedEmployee);
    }

    @Transactional
    public EmployeeResponseDto updateEmployee(String employeeId, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        existingEmployee.setFirstName(employeeDto.firstName());
        existingEmployee.setLastName(employeeDto.lastName());
        existingEmployee.setDivision(employeeDto.division());
        existingEmployee.setBuilding(employeeDto.building());
        existingEmployee.setTitle(employeeDto.title());
        existingEmployee.setRoom(employeeDto.room());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return mapToDto(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(String employeeId) {
        if (!employeeRepository.existsByEmployeeId(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
        employeeRepository.deleteByEmployeeId(employeeId);
    }
    @Transactional
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }
    private EmployeeResponseDto mapToDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDivision(),
                employee.getBuilding(),
                employee.getTitle(),
                employee.getRoom()
        );
    }

    private Employee mapToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.employeeId());
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setDivision(employeeDto.division());
        employee.setBuilding(employeeDto.building());
        employee.setTitle(employeeDto.title());
        employee.setRoom(employeeDto.room());
        return employee;
    }
}
