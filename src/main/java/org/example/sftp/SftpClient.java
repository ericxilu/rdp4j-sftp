package org.example.sftp;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SftpClient {
    @Value("${sftp.path}")
    private String path = "ftpserver";

    @Value("${sftp.host}")
    private String host = "localhost";

    @Value("${sftp.username}")
    private String username = "ericlu";

    @Value("${sftp.password}")
    private String password = "1943L1944t$&@";

    private JSch jsch = null;
    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;


    public void downloadFile(String remoteFile, String localFile) throws SftpException {
        log.info("downloading remote file {} to local file {}", remoteFile, localFile);
        channelSftp.get(remoteFile, localFile);


    }

    public void connect() throws IOException {
        jsch = new JSch();
        try {
            session = jsch.getSession(username, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

        } catch (JSchException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    public void disconnect() {
            if (channelSftp != null)
                channelSftp.disconnect();

            if (channel != null)
                channel.disconnect();

            if (session != null)
                session.disconnect();

    }


}
