package com.lucas.messaging;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 4/25/14  Time: 12:47 PM
 * This Class provide the implementation for the functionality of
 * writing the test cased using spring Junit runner
 */

@Profile(value = "default")
@Lazy(value = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/configuration/applicationContext.xml"})
public abstract class LucasMqTestContext implements ApplicationContextAware {

    protected static Logger LOG;
    protected ApplicationContext applicationContext;


    @Before
    public void init() {
        this.initLucasTestCase();
    }

    /**
     * initLucasTestCase()  can be override in the lucas test classes its
     * callback method for the lucas test case initialisation
     */
    protected void initLucasTestCase() {
    }

    protected LucasMqTestContext(final Class clazz) {
        this.LOG = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @After
    public void clean() {
        this.cleanLucasTestCase();
    }

    /**
     * cleanLucasTestCase() can be override the the lucas test classes its
     * call back method for the lucas test case clean activity
     */
    protected  void cleanLucasTestCase() {
    }
}
