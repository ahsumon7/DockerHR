package com.example.hr.service;

import com.example.hr.model.Employee;
import com.example.hr.model.EmployeeXml;
import com.example.hr.model.EmployeesXml;
import com.example.hr.repository.EmployeeRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeXmlService {

    private final EmployeeRepository employeeRepository;

    // Import from a file path (e.g. for file upload scenario)
    public void importFromXmlFilePath(String filePath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(EmployeesXml.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        EmployeesXml employeesXml = (EmployeesXml) unmarshaller.unmarshal(new File(filePath));

        saveEmployees(employeesXml);
    }

    // Import from classpath resource (recommended for resources/employees.xml)
    public void importFromXmlClasspath() throws Exception {
        ClassPathResource resource = new ClassPathResource("employees.xml");
        try (InputStream inputStream = resource.getInputStream()) {
            JAXBContext context = JAXBContext.newInstance(EmployeesXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            EmployeesXml employeesXml = (EmployeesXml) unmarshaller.unmarshal(inputStream);
            saveEmployees(employeesXml);
        }
    }

    // Helper to convert and save
    private void saveEmployees(EmployeesXml employeesXml) {
        for (EmployeeXml xml : employeesXml.getEmployees()) {
            employeeRepository.findByEmployeeId(xml.getId())
                    .map(existingEmployee -> {
                        // Update existing employee's fields
                        existingEmployee.setFirstName(xml.getFirstname());
                        existingEmployee.setLastName(xml.getLastname());
                        existingEmployee.setDivision(xml.getDivision());
                        existingEmployee.setBuilding(xml.getBuilding());
                        existingEmployee.setTitle(xml.getTitle());
                        existingEmployee.setRoom(xml.getRoom());
                        return employeeRepository.save(existingEmployee);
                    })
                    .orElseGet(() -> {
                        // Create new employee if not found
                        Employee newEmployee = new Employee();
                        newEmployee.setEmployeeId(xml.getId());
                        newEmployee.setFirstName(xml.getFirstname());
                        newEmployee.setLastName(xml.getLastname());
                        newEmployee.setDivision(xml.getDivision());
                        newEmployee.setBuilding(xml.getBuilding());
                        newEmployee.setTitle(xml.getTitle());
                        newEmployee.setRoom(xml.getRoom());
                        return employeeRepository.save(newEmployee);
                    });
        }

    }

    // Export employees to XML file on disk
    public void exportToXml(String filePath) throws Exception {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeXml> xmlList = employeeList.stream()
                .map(e -> {
                    EmployeeXml xml = new EmployeeXml();
                    xml.setId(e.getEmployeeId());
                    xml.setFirstname(e.getFirstName());
                    xml.setLastname(e.getLastName());
                    xml.setDivision(e.getDivision());
                    xml.setBuilding(e.getBuilding());
                    xml.setTitle(e.getTitle());
                    xml.setRoom(e.getRoom());
                    return xml;
                })
                .collect(Collectors.toList());

        EmployeesXml employeesXml = new EmployeesXml();
        employeesXml.setEmployees(xmlList);

        JAXBContext context = JAXBContext.newInstance(EmployeesXml.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(employeesXml, new File(filePath));
    }
}
