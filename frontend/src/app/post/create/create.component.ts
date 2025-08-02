import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';

import { EmployeeService } from '../employee-service.service';

@Component({
  selector: 'app-create-employee',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
})
export class CreateComponent implements OnInit {
  // ✅ Fix here

  form!: FormGroup;

  constructor(
    public employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      employeeId: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      division: new FormControl('', Validators.required),
      building: new FormControl('', Validators.required),
      title: new FormControl('', Validators.required),
      room: new FormControl('', Validators.required),
    });
  }

  get f() {
    return this.form.controls;
  }

  submit(): void {
    if (this.form.invalid) return;

    console.log(this.form.value);
    this.employeeService.create(this.form.value).subscribe(() => {
      console.log('Employee created successfully!');
      this.router.navigateByUrl('post/index');
    });
  }
}
