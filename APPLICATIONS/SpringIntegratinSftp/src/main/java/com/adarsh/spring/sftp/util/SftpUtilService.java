package com.adarsh.spring.sftp.util;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.integration.file.remote.RemoteFileTemplate;

/**
 * Created by Adarsh on 3/11/15.
 */
public interface SftpUtilService {

    void createTestFiles(RemoteFileTemplate<ChannelSftp.LsEntry> template, String... fileNames);

    void cleanUp(RemoteFileTemplate<ChannelSftp.LsEntry> template, String... fileNames);

    boolean fileExists(RemoteFileTemplate<ChannelSftp.LsEntry> template, String... fileNames);
}
