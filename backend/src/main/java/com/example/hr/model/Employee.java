package com.example.hr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated internal ID

    @Column(name = "employee_id", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Employee ID is required")
    @Size(max = 20, message = "Employee ID must be less than 20 characters")
    private String employeeId; // External ID shown in UI/search

    @Column(name = "first_name", nullable = false, length = 50)
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @Column(name = "division", nullable = false, length = 50)
    @NotBlank(message = "Division is required")
    @Size(max = 50, message = "Division must be less than 50 characters")
    private String division;

    @Column(name = "building", nullable = false, length = 50)
    @NotBlank(message = "Building is required")
    @Size(max = 50, message = "Building must be less than 50 characters")
    private String building;

    @Column(name = "title", nullable = false, length = 100)
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Column(name = "room", nullable = false, length = 20)
    @NotBlank(message = "Room is required")
    @Size(max = 20, message = "Room must be less than 20 characters")
    private String room;
}
