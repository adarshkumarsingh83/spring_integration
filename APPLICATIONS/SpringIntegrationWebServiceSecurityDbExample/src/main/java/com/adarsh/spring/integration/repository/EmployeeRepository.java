package com.adarsh.spring.integration.repository;

import com.adarsh.spring.integration.entities.Employee;

import java.util.List;

/**
 * Created by Adarsh on 3/27/15.
 */
public interface EmployeeRepository {

    public Employee getEmployeeById(Integer employeeId);

    public List<Employee> getEmployeeList();
}
