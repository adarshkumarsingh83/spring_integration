package com.lucas.service.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Adarsh
 * Created By: Adarsh
 * Created On Date: 4/25/14  Time: 11:29 AM
 * This Interface provide the specification for Tcp Communication using the Spring Integration
 */

public interface TcpCommunicationService {

    public static Logger LOG = LoggerFactory.getLogger(TcpCommunicationService.class);

    /**
     * sendToRemoteServer() provide the functionality for writing data on the remote machine
     * by established the connection between local
     * and remote machine using java.net.Socket passing remote hostname & port
     * then get the DataOutputStream of the remote machine with the help of
     * java.net.Socket class then write the token to remote machine DataOutputStream
     * and close the stream.
     *
     * @param token is the instance of the java.lang.String containing the token value
     * @throws RuntimeException
     */
    public void sendToRemoteServer(final String token) throws RuntimeException;

    /**
     * waitAndReceiveOnLocalServer() provide the functionality for receiving the data.
     * Listens on the specified port on localhost until it receives the quitToken
     * OR, if waitSeconds is > 0, if waitSeconds is reached, whichever occurs first.
     *
     * @param tokenToQuitOn is the instance of the java.lang.String containing the token value
     * @throws RuntimeException
     */
    public void waitAndReceiveOnLocalServer(final String tokenToQuitOn) throws RuntimeException;


    /**
     * isRoundTripSuccessful() provide the specification for the getting the status of the
     * execution of the process of the token round trip from client to server and again to client.
     *
     *
     * @return primitive boolean value consisting the status of the operation.
     */
    public boolean isRoundTripSuccessful();

    /**
     * calculateTimeTaken() provide the specification for the calculating the time
     * taken by the process.
     *
     */
    public void calculateTimeTaken();

    /**
     * startStopWatch() provide the specification for starting the stop watch to
     * monitor the time
     */
    public void startStopWatch();

    /**
     * getTokenForRoundTrip() provide the specification for the getting the token
     * send by client to the server
     */
    public String getTokenForRoundTrip();

    /**
     * getRoundTripMilliseconds() provide the specification for the getting the
     * time taken by the operation.
     */
    public long getRoundTripMilliseconds();
}
