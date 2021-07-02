package com.adarsh.spring.integration.bean;

import com.adarsh.spring.integration.entities.Employee;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "employeeId",
        "employeeFirstName",
        "employeeLastName",
        "employeeEmail",
        "employeePhone"
})
@XmlRootElement(name = "EmployeeBean")
@JsonPropertyOrder({ "employeeId","employeeFirstName","employeeLastName","employeeEmail","employeePhone"})
public class EmployeeBean {

    private Integer employeeId;

    private String employeeFirstName;

    private String employeeLastName;

    private String employeeEmail;

    private String employeePhone;

    public EmployeeBean() {
    }

    public EmployeeBean(Employee employee) {
        this.employeeId=employee.getEmployeeId();
        this.employeeFirstName = employee.getEmployeeFirstName();
        this.employeeLastName = employee.getEmployeeLastName();
        this.employeeEmail = employee.getEmployeeEmail();
        this.employeePhone = employee.getEmployeePhone();
    }

    public EmployeeBean(String employeeFirstName, String employeeLastName
            , String employeeEmail, String employeePhone) {
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }
}


