package com.adarsh.spring.integration.services;

import com.adarsh.spring.integration.entities.Employee;
import com.adarsh.spring.integration.entities.EmployeeList;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("employeeSearchService")
public class EmployeeSearchService {

	private static Logger logger = Logger.getLogger(EmployeeSearchService.class);
	/**
	 * The API <code>getEmployee()</code> looks up the mapped in coming message header's id param
	 * and fills the return object with the appropriate employee details. The return
	 * object is wrapped in Spring Integration Message with response headers filled in.
	 * This example shows the usage of URL path variables and how the service act on
	 * those variables.
	 * @param inMessage
	 * @return an instance of <code>{@link Message}</code> that wraps <code>{@link EmployeeList}</code>
	 */
	@Secured("ROLE_REST_HTTP_USER")
	public Message<EmployeeList> getEmployee(Message<?> inMessage){
	
		EmployeeList employeeList = new EmployeeList();
		Map<String, Object> responseHeaderMap = new HashMap<String, Object>();
		
		try{
			MessageHeaders headers = inMessage.getHeaders();
			String id = (String)headers.get("employeeId");
			boolean isFound;
			if (id.equals("1")){
				employeeList.getEmployee().add(new Employee(1, "John", "Doe"));
				isFound = true;
			}else if (id.equals("2")){
				employeeList.getEmployee().add(new Employee(2, "Jane", "Doe"));
				isFound = true;
			}else if (id.equals("0")){
				employeeList.getEmployee().add(new Employee(1, "John", "Doe"));
				employeeList.getEmployee().add(new Employee(2, "Jane", "Doe"));				
				isFound = true;
			}else{				
				isFound = false;
			}			
			if (isFound){
				setReturnStatusAndMessage("0", "Success", employeeList, responseHeaderMap);
			}else{
				setReturnStatusAndMessage("2", "Employee Not Found", employeeList, responseHeaderMap);								
			}
			
		}catch (Throwable e){
			setReturnStatusAndMessage("1", "System Error", employeeList, responseHeaderMap);
			logger.error("System error occurred :"+e);
		}
		Message<EmployeeList> message = new GenericMessage<EmployeeList>(employeeList, responseHeaderMap);
		return message;		
	}
	
	/**
	 * The API <code>setReturnStatusAndMessage()</code> sets the return status and return message
	 * in the return message payload and its header.
	 * @param status
	 * @param message
	 * @param employeeList
	 * @param responseHeaderMap
	 */
	private void setReturnStatusAndMessage(String status, 
						String message, 
						EmployeeList employeeList, 
						Map<String, Object> responseHeaderMap){
		
		employeeList.setReturnStatus(status);
		employeeList.setReturnStatusMsg(message);
		responseHeaderMap.put("Return-Status", status);
		responseHeaderMap.put("Return-Status-Msg", message);
	}
}


