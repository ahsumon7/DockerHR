package com.example.hr.dto;


public record EmployeeResponseDto(
        Long id,
        String employeeId,
        String firstName,
        String lastName,
        String division,
        String building,
        String title,
        String room
) {}
