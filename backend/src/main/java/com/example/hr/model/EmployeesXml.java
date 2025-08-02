package com.example.hr.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
//comment
@XmlRootElement(name = "employees")
public class EmployeesXml {
    private List<EmployeeXml> employees;

    @XmlElement(name = "employee")
    public List<EmployeeXml> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeXml> employees) {
        this.employees = employees;
    }


}
