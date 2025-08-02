package com.example.hr.repository;

import com.example.hr.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employee by exact empl
    Optional<Employee> findByEmployeeId(String employeeId);

    // Search employees by partial first or last name (case-insensitive)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> searchByName(@Param("name") String name);

    // Search employees by employeeId exact match OR partial first/last name match
    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId OR " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByEmployeeIdOrName(@Param("employeeId") String employeeId, @Param("name") String name);

    boolean existsByEmployeeId(String employeeId);
    void deleteByEmployeeId(String employeeId);
}
