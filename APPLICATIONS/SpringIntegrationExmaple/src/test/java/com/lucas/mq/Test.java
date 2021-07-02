package com.lucas.mq;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/8/14  Time: 3:01 PM
 * This Class provide the implementation for the functionality of..
 */

@Profile(value = "default")
@Lazy(value = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/configuration/applicationContext.xml"})
public class Test {

    private static final Logger LOGGER = Logger.getLogger(MainDriver.class);

    @org.junit.Test
    public void testFlow() {
        LOGGER.info("Welcome to Spring Integration! ");
        LOGGER.info(" Please enter some text ");
    }

}
