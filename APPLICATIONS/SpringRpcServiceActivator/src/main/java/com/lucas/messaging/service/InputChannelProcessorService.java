package com.lucas.messaging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.message.GenericMessage;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/3/14  Time: 9:30 PM
 * This Class provide the implementation for the functionality of..
 */

public interface InputChannelProcessorService {

    public static Logger LOG = LoggerFactory.getLogger(InputChannelProcessorService.class);

    /**
     * @param data
     * @throws Exception
     */
    public GenericMessage processRequest(@Payload Object data) throws Exception;
}
