package com.lucas.messaging.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/3/14  Time: 9:30 PM
 * This Class provide the implementation for the functionality of..
 */

@Lazy(value = false)
@Scope
@Service
public class InputChannelProcessorServiceImpl implements InputChannelProcessorService {

    @Override
    @ServiceActivator(inputChannel = "fromRabbit", outputChannel = "nullChannel")
    public GenericMessage processRequest(@Payload Object data) throws Exception {

        LOG.debug("\n\n\n\t\tInputChannelProcessorService ------>" + data);
        final Message<String> message = MessageBuilder.withPayload(data.toString().toUpperCase())
                .build();
        return (GenericMessage) message;
    }
}
