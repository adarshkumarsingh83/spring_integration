package com.adarsh.spring.sftp.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.SessionCallback;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Service(value = "sftpUtilServiceImpl")
public class SftpUtilServiceImpl implements SftpUtilService {


    @Value("${espark.sftp.server.location}")
    private String sftpRemoteServerDirLocation;


    @Value("${espark.sftp.file.tag}")
    private String sftpRemoteFileTag;

    @Override
	public void createTestFiles(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (template != null) {
			final ByteArrayInputStream stream = new ByteArrayInputStream(sftpRemoteFileTag.getBytes());
			template.execute(new SessionCallback<LsEntry, Void>() {

				@Override
				public Void doInSession(Session<LsEntry> session) throws IOException {
					try {
						session.mkdir(sftpRemoteServerDirLocation);
					}
					catch (Exception e) {
						assertThat(e.getMessage(), containsString("failed to create"));
					}
					for (int i = 0; i < fileNames.length; i++) {
						stream.reset();
						session.write(stream, sftpRemoteServerDirLocation+"/" + fileNames[i]);
					}
					return null;
				}
			});
		}
	}

    @Override
	public  void cleanUp(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (template != null) {
			template.execute(new SessionCallback<LsEntry, Void>() {

				@Override
				public Void doInSession(Session<LsEntry> session) throws IOException {

					ChannelSftp channel = (ChannelSftp) new DirectFieldAccessor(new DirectFieldAccessor(session)
							.getPropertyValue("targetSession")).getPropertyValue("channel");
					for (int i = 0; i < fileNames.length; i++) {
						try {
							session.remove(sftpRemoteServerDirLocation+"" + fileNames[i]);
						}
						catch (IOException e) {}
					}
					try {
						// should be empty
						channel.rmdir(sftpRemoteServerDirLocation);
					}
					catch (SftpException e) {
						fail("Expected remote directory to be empty " + e.getMessage());
					}
					return null;
				}
			});
		}
	}

    @Override
	public  boolean fileExists(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (template != null) {
			return template.execute(new SessionCallback<LsEntry, Boolean>() {

				@Override
				public Boolean doInSession(Session<LsEntry> session) throws IOException {

					ChannelSftp channel = (ChannelSftp) new DirectFieldAccessor(new DirectFieldAccessor(session)
							.getPropertyValue("targetSession")).getPropertyValue("channel");
					for (int i = 0; i < fileNames.length; i++) {
						try {
							SftpATTRS stat = channel.stat(sftpRemoteServerDirLocation+"/" + fileNames[i]);
							if (stat == null) {
								System.out.println("stat returned null for " + fileNames[i]);
								return false;
							}
						}
						catch (SftpException e) {
							System.out.println("Remote file not present: " + e.getMessage() + " : " + fileNames[i]);
							return false;
						}
					}
					return true;
				}
			});
		}
		else {
			return false;
		}
	}

}
