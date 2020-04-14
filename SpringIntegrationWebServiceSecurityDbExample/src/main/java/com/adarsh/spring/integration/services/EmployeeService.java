package com.adarsh.spring.integration.services;

import com.adarsh.spring.integration.bean.ResponseBean;
import org.springframework.messaging.Message;

/**
 * Created by Adarsh on 3/27/15.
 */
public interface EmployeeService {

    public Message<ResponseBean> getEmployee(Message<?> requestMessage);
}
