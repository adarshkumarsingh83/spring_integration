package com.adarsh.spring.sftp.test;

import com.adarsh.spring.sftp.util.SftpUtilService;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/applicationContextSftp-Inbound.xml"})
public class SftpInboundReceiveSample {


    @Autowired(required = true)
    @Qualifier(value = "sftpUtilsImpl")
    private SftpUtilService sftpUtilService;

    @Autowired(required = true)
    @Qualifier(value = "sftpSessionFactory")
    private SessionFactory<LsEntry> sessionFactory;

    @Value("${espark.sftp.file.tag}")
    private String sftpRemoteFileTag;

    @Autowired(required = true)
    @Qualifier(value = "sftpInputChannel")
    private PollableChannel localFileChannel;

    @Autowired(required = true)
    @Qualifier(value = "sftpInboundChannelAdapter")
    private SourcePollingChannelAdapter adapter;

	@Test
	public void testSftpInBoundFileReceiveFromServer(){
		final String file1 = "a.txt";
        final String file2 = "b.txt";
        final String file3 = "c.bar";
		new File("local-dir", file1).delete();
		new File("local-dir", file2).delete();
        final RemoteFileTemplate<LsEntry> template  = new RemoteFileTemplate<LsEntry>(sessionFactory);
        try {
			this.sftpUtilService.createTestFiles(template, file1, file2, file3);
			this.adapter.start();
			Message<?> received = localFileChannel.receive();
			assertNotNull("Expected file", received);
			System.out.println("Received first file message: " + received);
			received = localFileChannel.receive();
			assertNotNull("Expected file", received);
			System.out.println("Received second file message: " + received);
			received = localFileChannel.receive(1000);
			assertNull("Expected null", received);
			System.out.println("No third file was received as expected");
		}
		finally {

			this.sftpUtilService.cleanUp(template, file1, file2, file3);
			assertTrue("Could note delete retrieved file", new File("local-dir", file1).delete());
			assertTrue("Could note delete retrieved file", new File("local-dir", file2).delete());
		}
	}

}
