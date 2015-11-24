package com.adarsh.spring.mail.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/gmail-pop3-config.xml"})
public class GmailInboundPop3AdapterTestApp {

	private static Logger logger = Logger.getLogger(GmailInboundPop3AdapterTestApp.class);

    @Qualifier(value = "emailReceiveChannel")
    @Autowired(required = true)
    private DirectChannel inputChannel;

    @Test
	public void testGmailInboundPop3Adapter() throws Exception {

		inputChannel.subscribe(new MessageHandler() {
			public void handleMessage(Message<?> message) throws MessagingException {
				logger.info("Message: " + message);
			}
		});
	}
}
