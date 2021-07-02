package com.lucas.service.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 4/29/14  Time: 3:30 PM
 * This Class provide the implementation for the functionality of..
 */

public interface RabbitMqService {

    public static Logger LOG = LoggerFactory.getLogger(RabbitMqService.class);

    /**
     * sendMessage()
     *
     * @param message
     * @return
     */
    public boolean sendMessage(String message);

    /**
     * receivedMessage()
     *
     * @return
     */
    public String receivedMessage();
}
