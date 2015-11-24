package com.adarsh.spring.integration.services;

import com.adarsh.spring.integration.bean.EmployeeBean;
import com.adarsh.spring.integration.bean.ResponseBean;
import com.adarsh.spring.integration.entities.Employee;
import com.adarsh.spring.integration.repository.EmployeeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adarsh on 3/27/15.
 */
@Service(value = "employeeServiceImpl")
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    @Qualifier(value = "employeeRepositoryImpl")
    @Autowired(required = true)
    private EmployeeRepository employeeRepository;

    @Transactional
    @Override
    @Secured("ROLE_REST_HTTP_USER")
    public Message<ResponseBean> getEmployee(Message<?> requestMessage) {
        final ResponseBean responseBean = new ResponseBean();
        final Map<String, Object> responseHeaderMap = new HashMap<String, Object>();

        try {
            final MessageHeaders headers = requestMessage.getHeaders();
            Object o=headers.get("employeeId");
            if(o!=null){
                try{
                    final Integer employeeId=Integer.parseInt(o.toString().trim());
                    Employee employee=this.employeeRepository.getEmployeeById(employeeId);
                    responseBean.setEmployee(new EmployeeBean(employee));
                    setReturnStatusAndMessage("0", "Success", responseBean, responseHeaderMap);
                }catch (Exception e){
                    logger.error(e.getMessage());
                    o=null;
                }
            }
            if(o==null){
                final List<Employee>employeeList=this.employeeRepository.getEmployeeList();
                for(Employee employee:employeeList){
                    responseBean.setEmployee(new EmployeeBean(employee));
                }
                setReturnStatusAndMessage("2", "Employee is Not Found", responseBean, responseHeaderMap);
            }

        } catch (Throwable e) {
            setReturnStatusAndMessage("1", "System Error", responseBean, responseHeaderMap);
            logger.error("System error occurred :" + e);
        }
       final Message<ResponseBean> message =
               new GenericMessage<ResponseBean>(responseBean, responseHeaderMap);
        return message;
    }


    /**
     * The API <code>setReturnStatusAndMessage()</code> sets the return status and return message
     * in the return message payload and its header.
     * @param status
     * @param message
     * @param responseBean
     * @param responseHeaderMap
     */
    private void setReturnStatusAndMessage(String status,
                                           String message,
                                           ResponseBean responseBean,
                                           Map<String, Object> responseHeaderMap){

        responseBean.setReturnStatus(status);
        responseBean.setReturnStatusMsg(message);
        responseHeaderMap.put("Return-Status", status);
        responseHeaderMap.put("Return-Status-Msg", message);
    }

}
