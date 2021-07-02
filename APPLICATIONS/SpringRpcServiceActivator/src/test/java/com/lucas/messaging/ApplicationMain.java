package com.lucas.messaging;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/8/14  Time: 3:04 PM
 * This Class provide the implementation for the functionality of..
 */

public class ApplicationMain {

    private static final Logger LOG = Logger.getLogger(ApplicationMain.class);

    public static void main(final String... args) {

        LOG.info("         Welcome to Spring Integration!                ");
        final AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:/configuration/applicationContext.xml");
        context.registerShutdownHook();
        LOG.info(" Please enter some text ");

    }
}
