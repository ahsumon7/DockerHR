import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private apiURL = 'http://localhost:8080/api/v1/employees';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  getAll(): Observable<Employee[]> {
    return this.http
      .get<Employee[]>(this.apiURL)
      .pipe(catchError(this.errorHandler));
  }

  find(employeeId: string): Observable<Employee> {
    return this.http
      .get<Employee>(`${this.apiURL}/${employeeId}`)
      .pipe(catchError(this.errorHandler));
  }

  create(employee: Employee): Observable<Employee> {
    return this.http
      .post<Employee>(this.apiURL, employee, this.httpOptions)
      .pipe(catchError(this.errorHandler));
  }

  update(employeeId: string, employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(
      `${this.apiURL}/${employeeId}`,
      employee,
      this.httpOptions
    );
  }

  delete(employeeId: string): Observable<void> {
    return this.http
      .delete<void>(`${this.apiURL}/${employeeId}`, this.httpOptions)
      .pipe(catchError(this.errorHandler));
  }
  importFromXml(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post<string>(`${this.apiURL}/import`, formData);
  }
  private errorHandler(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
