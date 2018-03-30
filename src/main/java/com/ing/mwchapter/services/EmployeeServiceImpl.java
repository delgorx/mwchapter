package com.ing.mwchapter.services;

import com.ing.mwchapter.domain.Employee;
import com.ing.mwchapter.domain.Gender;
import com.ing.mwchapter.domain.Seniority;
import com.ing.mwchapter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> getListOfEmployeeNames() {

        return repository.getAllEmployees().stream().map(employee -> employee.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getListOfEmployeeFullNames() {
        return repository
                .getAllEmployees()
                .stream()
                .map(employee -> String.format("%s %s", employee.getName(), employee.getSurname()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getEmployeesWithSeniority(Seniority seniority) {

        return repository.getAllEmployees().stream().filter(employee -> employee.getSeniority().equals(seniority)).collect(Collectors.toList());
    }

    @Override
    public Map<Gender, List<Employee>> getEmployeesByGender() {
        return repository.getAllEmployees().stream().collect(Collectors.groupingBy(Employee::getGender));
    }

    @Override
    public Double getAverageSalary() {
        return repository.getAllEmployees().stream().mapToDouble(e -> e.getSalary()).average().getAsDouble();
    }

    @Override
    public Map<Seniority, Double> getAverageSalaryBySeniority() {
        return repository
                .getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getSeniority, Collectors.averagingDouble(Employee::getSalary)));
    }

    @Override
    public Optional<Employee> getEmployee(String name, String surname) {

        return repository.getAllEmployees().stream().filter(employee -> employee.getName().equals(name) && employee.getSurname().equals(surname)).findFirst();
    }

    @Override
    public Set<String> getSkills() {
        return repository.getAllEmployees().stream().flatMap(e -> e.getSkills().stream()).distinct().collect(Collectors.toSet());
    }
}
