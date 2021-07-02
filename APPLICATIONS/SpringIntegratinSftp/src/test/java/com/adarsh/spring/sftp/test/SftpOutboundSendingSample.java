package com.adarsh.spring.sftp.test;

import com.adarsh.spring.sftp.util.SftpUtilService;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/applicationContextSftp-Outbound.xml"})
public class SftpOutboundSendingSample {

    @Qualifier(value = "sftpUtilServiceImpl")
    @Autowired(required = true)
    private SftpUtilService sftpUtilService;

    @Autowired(required = true)
    @Qualifier(value = "sftpSessionFactory")
    private SessionFactory<LsEntry> sessionFactory;

    @Autowired(required = true)
    @Qualifier(value = "sftpInputChannel")
    private MessageChannel sftpInputChannel;

    @Value("${espark.sftp.file.tag}")
    private String sftpRemoteFileTag;


    @Test
    public void testSftpOutBoundFileTransferToServer() throws Exception{

        final String sourceFileName = "src\\main\\resources\\data\\README.md";
        final String destinationFileName = "data/README.md" +sftpRemoteFileTag;

		final RemoteFileTemplate<LsEntry> template = new RemoteFileTemplate<LsEntry>(sessionFactory);
        this.sftpUtilService.createTestFiles(template); // Just the directory

		try {
			final File file = new File(sourceFileName);
			Assert.isTrue(file.exists(), String.format("File '%s' does not exist.", sourceFileName));
			final Message<File> message = MessageBuilder.withPayload(file).build();
            this.sftpInputChannel.send(message);
			Thread.sleep(2000);
			Assert.isTrue(this.sftpUtilService.fileExists(template, destinationFileName));
			System.out.println(String.format("Successfully transferred '%s' file to a " +
					"remote location under the name '%s'", sourceFileName, destinationFileName));
		}
		finally {
            this.sftpUtilService.cleanUp(template, destinationFileName);
		}
	}

}
