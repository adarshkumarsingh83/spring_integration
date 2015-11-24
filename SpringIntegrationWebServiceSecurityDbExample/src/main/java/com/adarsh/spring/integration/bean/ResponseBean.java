package com.adarsh.spring.integration.bean;

import com.adarsh.spring.integration.entities.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adarsh on 3/27/15.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "employeeBeanList",
        "returnStatus",
        "returnStatusMsg"
})
@XmlRootElement(name = "EmployeeList")
@JsonPropertyOrder({ "returnStatus","returnStatusMsg","EmployeeBean"})
public class ResponseBean implements Serializable{

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @XmlElement(name = "EmployeeBean", required = true)
    private List<EmployeeBean> employeeBeanList;

    @XmlElement(name = "returnStatus", required = true)
    private String returnStatus;

    @XmlElement(name = "returnStatusMsg", required = true)
    private String returnStatusMsg;

    public ResponseBean() {
        this.employeeBeanList = new ArrayList<>();
    }

    public void setEmployee(EmployeeBean employee) {
        this.employeeBeanList.add(employee);
    }

    public void setEmployee(List<EmployeeBean> employeeBeanList) {
        this.employeeBeanList = employeeBeanList;
    }

    public List<EmployeeBean> getEmployee() {
        if (employeeBeanList == null) {
            employeeBeanList = new ArrayList<EmployeeBean>();
        }
        return employeeBeanList;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnStatusMsg() {
        return returnStatusMsg;
    }

    public void setReturnStatusMsg(String returnStatusMsg) {
        this.returnStatusMsg = returnStatusMsg;
    }
}
