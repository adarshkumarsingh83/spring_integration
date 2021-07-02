package com.lucas.messaging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.message.GenericMessage;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/3/14  Time: 11:06 PM
 * This Class provide the implementation for the functionality of..
 */

public interface OutputChannelProcessorService {
    public static Logger LOG = LoggerFactory.getLogger(OutputChannelProcessorService.class);

    /**
     *
     * @param data
     * @throws Exception
     */
    public void processResponse(@Payload Object data,@Header("correlationId")String correlationId)
            throws Exception;
}
