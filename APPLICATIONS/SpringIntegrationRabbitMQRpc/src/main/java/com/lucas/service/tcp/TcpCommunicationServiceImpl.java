package com.lucas.service.tcp;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 4/25/14  Time: 11:29 AM
 * This Class provide the implementation for the functionality of..
 */


@Scope(value = "prototype")
@Service
public final class TcpCommunicationServiceImpl
        implements TcpCommunicationService {

    @Value("${app.lucas.tcp.host}")
    private String remoteHostToSendTo;

    @Value("${app.lucas.inbound.tcp.port}")
    private String remoteHostTcpPort;

    @Value("${app.lucas.outbound.tcp.port}")
    private String localHostPort;

    @Value("${app.lucas.waiting.time}")
    private String waitingTime;

    private String tokenForRoundTrip;
    private int remoteHostPort;
    private int localPortToListenOn;
    private int secondsToListenForBeforeBreaking;

    private boolean roundTripSuccessful;
    private long roundTripMilliseconds;

    @Autowired(required = true)
    @Qualifier(value = "stopWatch")
    private StopWatch stopWatch;

    @PostConstruct
    public void initService() {
        LOG.debug("TcpCommunicationServiceImpl initService() ");
        this.remoteHostPort = Integer.parseInt(remoteHostTcpPort);
        this.localPortToListenOn = Integer.parseInt(localHostPort);
        this.secondsToListenForBeforeBreaking = Integer.parseInt(waitingTime);
    }

    public TcpCommunicationServiceImpl() {
    }

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
    @Override
    public void sendToRemoteServer(final String token) throws RuntimeException {
        this.tokenForRoundTrip = token;
        Socket clientSocket = null;
        DataOutputStream outToServer = null;
        try {
            clientSocket = new Socket(InetAddress.getByName(this.remoteHostToSendTo), this.remoteHostPort);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(token);
            this.writeByte(outToServer, '\r', true);
            this.writeByte(outToServer, '\n', true);
            LOG.info("\n\nData Send To Remote Server : " + token + "\n");
        } catch (Exception e) {
            LOG.error("Exception Generated : ", e.getLocalizedMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (outToServer != null) {
                    outToServer.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (Exception e) {
                LOG.error("Exception Generated : ", e.getLocalizedMessage());
            }
        }
    }

    /**
     * waitAndReceiveOnLocalServer() provide the functionality for receiving the data.
     * Listens on the specified <code>port</code> on localhost until it receives the <code>quitToken</code>
     * OR, if <code>waitSeconds</code> is > 0, if waitSeconds is reached, whichever occurs first.
     *
     * @param tokenToQuitOn
     */
    @Override
    public void waitAndReceiveOnLocalServer(final String tokenToQuitOn) throws RuntimeException {
        final Thread thread = new Thread(new Runnable() {

            public void run() {
                String clientSentence = "";
                ServerSocket serverSocket = null;
                Socket clientSocket = null;
                try {
                    LOG.debug("Going to open port " + localPortToListenOn);
                    serverSocket = new ServerSocket(localPortToListenOn);
                    LOG.debug("Opened port " + localPortToListenOn);
                    while (true) {
                        LOG.debug("Going to accept");
                        clientSocket = serverSocket.accept();
                        LOG.debug("Accepted");
                        BufferedReader inFromClient = new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()));

                        clientSentence = inFromClient.readLine();
                        LOG.info("\n\nReceived Data From Client : " + clientSentence + "\n");
                        if (StringUtils.equals(clientSentence, tokenToQuitOn)) {
                            if (stopWatch != null) {
                                stopWatch.stop();
                            }
                            setRoundTripSuccessful(true);
                            LOG.info("My work here is done! Received quitToken, bailing out...");
                            break;
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Exception Generated : ", e.getLocalizedMessage());
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (clientSocket != null) {
                            clientSocket.close();
                        }
                        if (serverSocket != null) {
                            serverSocket.close();
                        }
                    } catch (Exception e) {
                        LOG.error("Exception Generated : ", e.getLocalizedMessage());
                    }
                }
            }
        });

        thread.setDaemon(true);
        thread.start();

        if (thread.isAlive() && !thread.isInterrupted()) {
            //Thread is still alive and kicking...
            if (this.secondsToListenForBeforeBreaking > 0) {
                // wait if waitSeconds is specified;
                try {
                    StopWatch miniWatch = new StopWatch();
                    miniWatch.start();
                    //Check every 100 ms if the job is finished via the roundTripSuccessful flag.
                    while (!this.roundTripSuccessful && (miniWatch.getTotalTimeMillis() < (this.secondsToListenForBeforeBreaking * 1000))) {
                        thread.sleep(100);
                    }
                    if (stopWatch != null && stopWatch.isRunning()) {
                        stopWatch.stop();
                    }
                    if (thread.isAlive()) {
                        thread.interrupt();
                    }
                    LOG.info(String.format("Interrupting wait after %s seconds", miniWatch.getTotalTimeMillis()));
                    miniWatch.stop();
                } catch (Exception e) {
                    LOG.warn("Error while waiting for thread completion!", e);
                }
            }
        }

    }

    /**
     * writeByte()
     *
     * @param outputStream
     * @param b
     * @param noDelay
     * @throws Exception
     */
    private void writeByte(OutputStream outputStream, int b, boolean noDelay) throws Exception {
        outputStream.write(b);
        LOG.trace("Wrote 0x" + Integer.toHexString(b));
        if (noDelay) {
            return;
        }
        Thread.sleep(500);
    }

    @Override
    public void startStopWatch() {
        stopWatch.start();
    }

    @Override
    public void calculateTimeTaken() {
        long elapsed = stopWatch.getTotalTimeMillis();
        this.setRoundTripMilliseconds(elapsed);
    }

    @Override
    public boolean isRoundTripSuccessful() {
        return roundTripSuccessful;
    }

    public void setRoundTripSuccessful(boolean roundTripSuccessful) {
        this.roundTripSuccessful = roundTripSuccessful;
    }

    @Override
    public long getRoundTripMilliseconds() {
        return roundTripMilliseconds;
    }

    public void setRoundTripMilliseconds(long roundTripMilliseconds) {
        this.roundTripMilliseconds = roundTripMilliseconds;
    }

    @Override
    public String getTokenForRoundTrip() {
        return tokenForRoundTrip;
    }

    @PreDestroy
    public void cleanService() {
        LOG.debug("TcpCommunicationServiceImpl cleanService()");
    }
}
