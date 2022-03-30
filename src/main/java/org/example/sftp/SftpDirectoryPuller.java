package org.example.sftp;

import java.util.concurrent.TimeUnit;


import lombok.extern.slf4j.Slf4j;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;
import org.example.fileservice.RemoteRefDataFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class SftpDirectoryPuller {
    @Value("${sftp.path}")
    private String path = "ftpserver";

    @Value("${sftp.host}")
    private String host = "localhost";

    @Value("${sftp.username}")
    private String username = "ericlu";

    @Value("${sftp.password}")
    private String password = "1943L1944t$&@";

    private DirectoryPoller dp;

    private RemoteRefDataFileService remoteRefDataFileService;

    public SftpDirectoryPuller(final RemoteRefDataFileService remoteRefDataFileService) {
        this.remoteRefDataFileService = remoteRefDataFileService;
    }



    public void stop(){
        dp.stop();
    }



    @PostConstruct
    private void doMain()  {


        log.info("monitoring directory: {} ",  path);
        PolledDirectory polledDirectory = new SftpDirectory(host, path, username, password);

         dp = DirectoryPoller.newBuilder()
                .addPolledDirectory(polledDirectory)
                .addListener(new SftpDirectoryPullerListener(remoteRefDataFileService))
                .enableFileAddedEventsForInitialContent()
                .setPollingInterval(10, TimeUnit.SECONDS)
                .start();

        }

}
