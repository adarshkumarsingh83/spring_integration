package com.adarsh.spring.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/8/14  Time: 5:45 PM
 * This Class provide the implementation for the functionality of..
 */
public class HelloWorld {
    private final static Logger logger = LoggerFactory
            .getLogger(HelloWorld.class);

    public String greet(String name) {
        logger.debug("\n\n\tGreeting {}", name+"\n");
        return "Hello " + name;
    }
}