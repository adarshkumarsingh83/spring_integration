package com.adarsh.spring.sftp.test;

import com.adarsh.spring.sftp.ToSftpFlowGateway;
import com.adarsh.spring.sftp.util.SftpUtilService;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/applicationContextSftp-Gateway.xml"})
public class SftpOutboundGatewaySample {


    @Autowired(required = true)
    @Qualifier(value = "sftpUtilsImpl")
    private SftpUtilService sftpUtilService;

    @Autowired(required = true)
    @Qualifier(value = "sftpSessionFactory")
    private SessionFactory<LsEntry> sessionFactory;

    @Autowired(required = true)
    @Qualifier(value = "toSftpFlowGateway")
    private ToSftpFlowGateway toSftpFlowGateway;

    @Value("${espark.sftp.server.location}")
    private String sftpRemoteServerDirLocation;


    @Test
    public void testLsGetRmCommandOnSftpServer() throws Exception {

        final RemoteFileTemplate<LsEntry> template = new RemoteFileTemplate<LsEntry>(sessionFactory);
        final String file1 = "1.sftpTest";
        final String file2 = "2.sftpTest";
        final File tmpDir = new File(System.getProperty("java.io.tmpdir"));

        try {
            // remove the previous output files if necessary
            new File(tmpDir, file1).delete();
            new File(tmpDir, file2).delete();

            this.sftpUtilService.createTestFiles(template, file1, file2);

            // execute the flow (ls, get, rm, aggregate results)
            final List<Boolean> rmResults = this.toSftpFlowGateway.lsGetAndRmFiles(sftpRemoteServerDirLocation);

            //Check everything went as expected, and clean up
            assertEquals(2, rmResults.size());
            for (Boolean result : rmResults) {
                assertTrue(result);
            }

        } finally {
            this.sftpUtilService.cleanUp(template, file1, file2);
            assertTrue("Could note delete retrieved file", new File(tmpDir, file1).delete());
            assertTrue("Could note delete retrieved file", new File(tmpDir, file2).delete());
        }
    }

}



