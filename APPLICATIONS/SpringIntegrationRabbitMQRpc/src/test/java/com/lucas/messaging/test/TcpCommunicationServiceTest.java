package com.lucas.messaging.test;

import com.lucas.service.mq.RabbitMqService;
import com.lucas.service.tcp.TcpCommunicationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @Author Adarsh
 * Created By: Adarsh
 * Created On Date: 4/25/14  Time: 12:42 PM
 * This Class provide the implementation for the functionality of..
 */
public class TcpCommunicationServiceTest extends LucasMqTestContext {

    @Autowired
    private TcpCommunicationService tcpCommunicationService;


    @Autowired
    private RabbitMqService rabbitMqService;

    public TcpCommunicationServiceTest() {
        super(TcpCommunicationServiceTest.class);
    }

    @Override
    protected void initLucasTestCase() {
        LOG.info("\n\n############ TcpCommunicationServiceTest initLucasTestCase() ############\n");
    }

    @Test
    public void testSendAndReceiveSingle() {

        this.tcpCommunicationService.startStopWatch();
        this.tcpCommunicationService.sendToRemoteServer(System.getProperty("user.name"));
        this.tcpCommunicationService.waitAndReceiveOnLocalServer(System.getProperty("user.name"));
        this.tcpCommunicationService.calculateTimeTaken();
        if (this.tcpCommunicationService.isRoundTripSuccessful()) {
            LOG.info("\n\n\n\t\t ************************** Round Trip is Successful "
                    + this.tcpCommunicationService.getTokenForRoundTrip()
                    + " Time Taken " + this.tcpCommunicationService.getRoundTripMilliseconds()
                    + " **************************  \n\n\n");
        } else {
            LOG.info("\"\\n\\n\\n\\t\\t ************************** Round Trip is UnSuccessful  " +
                    this.tcpCommunicationService.getTokenForRoundTrip() +
                    "**************************  \n\n\n");
        }
    }


    @Test
    public void testSendAndReceiveMultiple() {

        /* Thread First */
        new Thread() {
            private TcpCommunicationService tcpCommunicationService;

            public Thread setTcpCommunicationService(TcpCommunicationService tcpCommunicationService) {
                this.tcpCommunicationService = tcpCommunicationService;
                return this;
            }

            @Override
            public void run() {
                this.tcpCommunicationService.startStopWatch();
                this.tcpCommunicationService.sendToRemoteServer(System.getProperty("user.name"));
                this.tcpCommunicationService.waitAndReceiveOnLocalServer(System.getProperty("user.name"));
                this.tcpCommunicationService.calculateTimeTaken();
                if (this.tcpCommunicationService.isRoundTripSuccessful()) {
                    LOG.info("\n\n\n\t\t ************************** Round Trip is Successful "
                            + this.tcpCommunicationService.getTokenForRoundTrip()
                            + " Time Taken " + this.tcpCommunicationService.getRoundTripMilliseconds()
                            + " **************************  \n\n\n");
                } else {
                    LOG.info("\"\\n\\n\\n\\t\\t ************************** Round Trip is UnSuccessful  " +
                            this.tcpCommunicationService.getTokenForRoundTrip() +
                            "**************************  \n\n\n");
                }
            }
        }.setTcpCommunicationService(tcpCommunicationService).start();


        /*   Thread Second */
        new Thread() {

            private TcpCommunicationService tcpCommunicationService;

            public Thread setTcpCommunicationService(TcpCommunicationService tcpCommunicationService) {
                this.tcpCommunicationService = tcpCommunicationService;
                return this;
            }

            @Override
            public void run() {
                this.tcpCommunicationService.startStopWatch();
                this.tcpCommunicationService.sendToRemoteServer(System.getProperty("user.name"));
                this.tcpCommunicationService.waitAndReceiveOnLocalServer(System.getProperty("user.name"));
                this.tcpCommunicationService.calculateTimeTaken();
                if (this.tcpCommunicationService.isRoundTripSuccessful()) {
                    LOG.info("\n\n\n\t\t ************************** Round Trip is Successful "
                            + this.tcpCommunicationService.getTokenForRoundTrip()
                            + " Time Taken " + this.tcpCommunicationService.getRoundTripMilliseconds()
                            + " **************************  \n\n\n");
                } else {
                    LOG.info("\"\\n\\n\\n\\t\\t ************************** Round Trip is UnSuccessful  " +
                            this.tcpCommunicationService.getTokenForRoundTrip() +
                            "**************************  \n\n\n");
                }
            }
        }.setTcpCommunicationService(tcpCommunicationService).start();
    }


    @Test
    public void testAmqpClient() {
        LOG.info("\n\n\t\tMessage Send to Server "+this.rabbitMqService.sendMessage(System.getProperty("user.name")));
        LOG.info("\n\n\t\tMessage Received from Server "+this.rabbitMqService.receivedMessage());
    }

    @Override
    protected void cleanLucasTestCase() {
        LOG.info("\n\n############ TcpCommunicationServiceTest cleanLucasTestCase() ############\n");
    }
}
