package com.lucas.mq;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/8/14  Time: 3:01 PM
 * This Class provide the implementation for the functionality of..
 */
public final class MainDriver {

    private static final Logger LOGGER = Logger.getLogger(MainDriver.class);


    public static void main(final String... args) {

        LOGGER.info("MainDriver Class Main()");
        final AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:/configuration/applicationContext.xml");
        context.registerShutdownHook();
        LOGGER.info("Enter some text ");

    }
}
