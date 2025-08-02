import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { EmployeeService } from '../employee-service.service';

@Component({
  selector: 'app-edit-employee',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // <--- add ReactiveFormsModule here
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css'],
})
export class EditComponent implements OnInit {
  form!: FormGroup;
  employeeId!: string;

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.employeeId = this.route.snapshot.params['postId'];

    this.form = new FormGroup({
      employeeId: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      division: new FormControl('', Validators.required),
      building: new FormControl('', Validators.required),
      title: new FormControl('', Validators.required),
      room: new FormControl('', Validators.required),
    });

    this.employeeService.find(this.employeeId).subscribe({
      next: (employee) => this.form.patchValue(employee),
      error: (err) => console.error('Failed to load employee', err),
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    this.employeeService.update(this.employeeId, this.form.value).subscribe({
      next: () => {
        console.log('Employee updated successfully');
        this.router.navigateByUrl('post/index');
      },
      error: (err) => console.error('Update failed', err),
    });
  }
}
