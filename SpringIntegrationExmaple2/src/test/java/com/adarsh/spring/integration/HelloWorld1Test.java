package com.adarsh.spring.integration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/8/14  Time: 5:56 PM
 * This Class provide the implementation for the functionality of..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/configuration/applicationContext.xml"})
public class HelloWorld1Test {

    private final static Logger logger = LoggerFactory
            .getLogger(HelloWorld1Test.class);

    @Autowired
    private Greetings greetings;

    @Test
    public void test() {
        this.greetings.send("World");
        assertEquals(this.greetings.receive(), "Hello World");
        logger.debug("Spring Integration with gateways.");
    }

}