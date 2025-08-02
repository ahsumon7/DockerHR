package com.example.hr.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeDto(
        @NotBlank(message = "Employee ID is required")
        @Size(max = 20, message = "Employee ID must be less than 20 characters")
        String employeeId,

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must be less than 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must be less than 50 characters")
        String lastName,

        @NotBlank(message = "Division is required")
        @Size(max = 50, message = "Division must be less than 50 characters")
        String division,

        @NotBlank(message = "Building is required")
        @Size(max = 50, message = "Building must be less than 50 characters")
        String building,

        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must be less than 100 characters")
        String title,

        @NotBlank(message = "Room is required")
        @Size(max = 20, message = "Room must be less than 20 characters")
        String room
) {}
