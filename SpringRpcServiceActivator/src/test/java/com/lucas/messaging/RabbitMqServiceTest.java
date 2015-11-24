package com.lucas.messaging;

import com.lucas.messaging.service.GatewayService;
import com.lucas.messaging.service.RabbitMqService;
import org.junit.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.amqp.AmqpHeaders;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.support.MessageBuilder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;


/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 4/25/14  Time: 12:42 PM
 * This Class provide the implementation for the functionality of..
 */
public class RabbitMqServiceTest extends LucasMqTestContext {

    @Value("${app.lucas.queue}")
    private String queueName;

    @Autowired
    @Qualifier("correlationIdSet")
    private HashSet correlationIdSet;

   /* @Autowired(required = true)
    private GatewayService gatewayService; */

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    @Qualifier(value = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(value ="rabbitTemplateResponse")
    private RabbitTemplate rabbitTemplateResponse;


    public RabbitMqServiceTest() {
        super(RabbitMqServiceTest.class);
    }

    @Override
    protected void initLucasTestCase() {
        LOG.info("\n\n############ com.lucas.messaging.RabbitMqServiceTest initLucasTestCase() ############\n");
    }


    @Test
    public void testAmqpClient() {
        LOG.info("\n\n\t\tMessage Send to Server " + this.rabbitMqService.sendMessage(System.getProperty("user.name")));
        LOG.info("\n\n\t\tMessage Received from Server " + this.rabbitMqService.receivedMessage());

    }


   /* @Test
    public void testInputOutput() throws Exception {
        this.gatewayService.publishMessage(System.getProperty("user.name").toLowerCase());
    }
*/
    @Test
    public void testMessagePost() throws Exception {
        /*for (int index = 0; index < 10; index++) {*/
        final String corrId = UUID.randomUUID().toString();
        this.correlationIdSet.add(corrId);
        final DirectChannel toRabbit = (DirectChannel) applicationContext.getBean("toRabbit");
        final Message<String> msg = MessageBuilder.withPayload(
                System.getProperty("user.name")/*+"_"+index*/)
                .setCorrelationId(corrId)
                .setReplyChannel(toRabbit)
                .build();
        toRabbit.send(msg);
          /*  org.springframework.amqp.core.Message messageResp=rabbitTemplateResponse.receive();
            System.out.println(messageResp);*/
       /* }*/
    }


    @Test
    public void receive() {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        Object message = rabbitTemplate.receiveAndConvert();
        if (message instanceof String) {
            String strMessage = (String) message;
            LOG.info("String message received is '" + strMessage + "'");
        } else {
            LOG.info("Unknown message received '" + message + "'");
        }
    }

    @Test
    public void testMessageGatewayPost() throws Exception {
        final String corrId = UUID.randomUUID().toString();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(corrId.getBytes());
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.setContentType("application/text");
        messageProperties.setMessageId("100");
        messageProperties.setHeader("correlationId", corrId);
        org.springframework.amqp.core.Message message = new org.springframework.amqp.core.Message(System.getProperty("user.name").getBytes(), messageProperties);
        rabbitTemplate.send(message);
      /*  org.springframework.amqp.core.Message messageResp=rabbitTemplate.receive();
        System.out.println(messageResp);*/
    }


    @Override
    protected void cleanLucasTestCase() {
        LOG.info("\n\n############ com.lucas.messaging.RabbitMqServiceTest cleanLucasTestCase() ############\n");
    }
}
