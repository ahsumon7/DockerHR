import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { EmployeeService } from '../employee-service.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-view-employee',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css'],
})
export class ViewComponent implements OnInit {
  employeeId!: string;
  employee!: Employee;

  constructor(
    public employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // ✅ Corrected param name from 'employeeId' to 'postId'
    this.employeeId = this.route.snapshot.params['postId'];

    this.employeeService.find(this.employeeId).subscribe({
      next: (data: Employee) => {
        this.employee = data;
      },
      error: (err) => {
        console.error('Failed to load employee', err);
      },
    });
  }
}
