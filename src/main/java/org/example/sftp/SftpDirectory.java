package org.example.sftp;

import java.io.IOException;
import java.util.Set;

import java.util.TreeSet;
import java.util.Vector;

import com.github.drapostolos.rdp4j.spi.FileElement;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SftpDirectory implements PolledDirectory{
    private String host;
    private String workingDirectory;
    private String username;
    private String password;

    public SftpDirectory(String host, String workingDirectory, String username, String password) {
        log.info("sftp host: {}, workingDirectory: {}, username: {}, password: {}", host, workingDirectory, username, password);
        this.host = host;
        this.workingDirectory = workingDirectory;
        this.username = username;
        this.password = password;
    }

    public Set<FileElement> listFiles() throws IOException
    {
        Set<FileElement> result = new TreeSet<>();

        JSch jsch = new JSch();
        Session session ;
        try {
            session = jsch.getSession( username, host, 22 );
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword( password );
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            Vector<LsEntry> filesList = sftpChannel.ls( workingDirectory );
            for(LsEntry file : filesList)
            {
                result.add( new SftpFile( file ) );

            }
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
            throw new IOException(e);
        } catch (SftpException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        log.debug("remote sftp server list of files {}", result);
        return result;
    }
}