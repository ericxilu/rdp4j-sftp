package org.example.sftp;

import java.util.concurrent.TimeUnit;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;
import org.example.fileservice.RemoteRefDataFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@Getter
public class SftpDirectoryPuller {
    @Value("${sftp.path}")
    private String path;

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    private DirectoryPoller dp;

    private RemoteRefDataFileService remoteRefDataFileService;

    private SftpFileFilter sftpFileFilter;

    public SftpDirectoryPuller(final RemoteRefDataFileService remoteRefDataFileService,
                               final UnixSftpFileFilter unixSftpFIleFilter) {
        this.remoteRefDataFileService = remoteRefDataFileService;
        this.sftpFileFilter = unixSftpFIleFilter;
    }

    public void stop() {
        dp.stop();
    }


    @PostConstruct
    private void doMain() {
        log.info("monitoring directory: {} ", path);
        PolledDirectory polledDirectory = new SftpDirectory(host, path, username, password);

        dp = DirectoryPoller.newBuilder()
                .addPolledDirectory(polledDirectory)
                .addListener(new SftpDirectoryPullerListener(remoteRefDataFileService, sftpFileFilter))
                .enableFileAddedEventsForInitialContent()
                .setPollingInterval(10, TimeUnit.SECONDS)
                .start();

    }

}
