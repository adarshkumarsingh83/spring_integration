package com.lucas.messaging.service;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 5/8/14  Time: 6:50 PM
 * This Class provide the implementation for the functionality of..
 */

public interface GatewayService {

    public void publishMessage(final String msg);

    public String subscribeMessage();
}
