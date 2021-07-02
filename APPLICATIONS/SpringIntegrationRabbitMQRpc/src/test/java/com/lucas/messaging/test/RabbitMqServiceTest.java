package com.lucas.messaging.test;

import com.lucas.service.mq.RabbitMqService;
import com.lucas.service.tcp.TcpCommunicationService;
import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 4/25/14  Time: 12:42 PM
 * This Class provide the implementation for the functionality of..
 */
public class RabbitMqServiceTest extends LucasMqTestContext {



    @Autowired
    private RabbitMqService rabbitMqService;

    public RabbitMqServiceTest() {
        super(RabbitMqServiceTest.class);
    }

    @Override
    protected void initLucasTestCase() {
        LOG.info("\n\n############ RabbitMqServiceTest initLucasTestCase() ############\n");
    }


    @Test
    public void testAmqpClient() {
        LOG.info("\n\n\t\tMessage Send to Server "+this.rabbitMqService.sendMessage(System.getProperty("user.name")));
        LOG.info("\n\n\t\tMessage Received from Server "+this.rabbitMqService.receivedMessage());
    }

    @Override
    protected void cleanLucasTestCase() {
        LOG.info("\n\n############ RabbitMqServiceTest cleanLucasTestCase() ############\n");
    }
}
