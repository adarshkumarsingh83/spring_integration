package com.lucas.messaging.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.message.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/3/14  Time: 11:07 PM
 * This Class provide the implementation for the functionality of..
 */
@Lazy(value = false)
@Scope
@Service
public class OutputChannelProcessorServiceImpl implements OutputChannelProcessorService {

    @Autowired
    @Qualifier("correlationIdSet")
    private HashSet correlationIdSet;

    @Override
    @ServiceActivator(inputChannel = "fromRabbit")
    public void processResponse(@Payload Object data, @Header("correlationId") String correlationId) throws Exception {
        LOG.debug(this.correlationIdSet.contains(correlationId)
                ? "\n\n\t" + data + " Request and Response has same correlationId " + correlationId + "\n"
                : "\n\n\t\"+data+\"Request and Response hasn't same correlationId " + correlationId + "\n");
        this.correlationIdSet.remove(correlationId);
    }
}
