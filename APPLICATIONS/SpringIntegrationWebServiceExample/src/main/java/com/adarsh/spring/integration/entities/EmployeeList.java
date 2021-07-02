package com.adarsh.spring.integration.entities;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "employee",
    "returnStatus",
    "returnStatusMsg"
})
@XmlRootElement(name = "EmployeeList")
public class EmployeeList {
	
	@XmlElement(name = "Employee", required = true)
	private List<Employee> employee;
	
	@XmlElement(name = "returnStatus", required = true)
	private String returnStatus;
	
	@XmlElement(name = "returnStatusMsg", required = true)
	private String returnStatusMsg;

	/**
	 * @return the employee
	 */
	public List<Employee> getEmployee() {
		if (employee == null){
			employee = new ArrayList<Employee>();
		}
		return employee;
	}

	/**
	 * @return the returnStatus
	 */
	public String getReturnStatus() {
		return returnStatus;
	}

	/**
	 * @param returnStatus the returnStatus to set
	 */
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * @return the returnStatusMsg
	 */
	public String getReturnStatusMsg() {
		return returnStatusMsg;
	}

	/**
	 * @param returnStatusMsg the returnStatusMsg to set
	 */
	public void setReturnStatusMsg(String returnStatusMsg) {
		this.returnStatusMsg = returnStatusMsg;
	}
}


