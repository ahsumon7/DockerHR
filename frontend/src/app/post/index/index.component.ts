import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { EmployeeService } from '../employee-service.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
  employees: Employee[] = [];
  selectedFile: File | null = null;
  searchEmployeeId: string = '';

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchEmployees();
  }

  fetchEmployees(): void {
    this.employeeService.getAll().subscribe({
      next: (data: Employee[]) => {
        this.employees = data;
      },
      error: (error) => {
        console.error('Error loading employees:', error);
      },
    });
  }

  deleteEmployee(employeeId: string): void {
    this.employeeService.delete(employeeId).subscribe({
      next: () => {
        this.employees = this.employees.filter(
          (emp) => emp.employeeId !== employeeId
        );
        console.log('Employee deleted successfully!');
      },
      error: (error) => {
        console.error('Error deleting employee:', error);
      },
    });
  }
  updateEmployee(employeeId: string): void {
    this.router.navigateByUrl(`/post/${employeeId}/edit`);
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    } else {
      this.selectedFile = null;
    }
  }

  importEmployees(): void {
    if (!this.selectedFile) return;

    this.employeeService.importFromXml(this.selectedFile).subscribe({
      next: (response) => {
        console.log('Import success:', response);
        this.fetchEmployees();
      },
      error: (error) => {
        console.error('Import failed:', error);
      },
    });
  }

  searchEmployeeById(): void {
    const id = this.searchEmployeeId.trim();
    if (id) {
      this.router.navigateByUrl(`/post/${id}/view`);
    } else {
      // Optional: maybe reload the full employee list if input is empty
      this.fetchEmployees();
    }
  }
}
