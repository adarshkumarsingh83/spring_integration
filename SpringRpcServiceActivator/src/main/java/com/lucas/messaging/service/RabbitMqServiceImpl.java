package com.lucas.messaging.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 4/29/14  Time: 3:30 PM
 * This Class provide the implementation for the functionality of..
 */

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    @Qualifier(value = "rabbitTemplate")
    private RabbitTemplate amqpTemplate;

    @Override
    public boolean sendMessage(final String message) {
        try {
            this.amqpTemplate.convertAndSend(message);
        } catch (Exception e) {
            LOG.error("Exception Generated " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String receivedMessage() {
        String message = "";
        try {
            message = (String) this.amqpTemplate.receiveAndConvert();
        } catch (Exception e) {
            LOG.error("Exception Generated " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return message;
    }

}
