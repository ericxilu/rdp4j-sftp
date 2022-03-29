package org.example.sftp;

import java.util.concurrent.TimeUnit;


import lombok.extern.slf4j.Slf4j;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class FtpExample {
//    @Option(name="-path")
    private String path = "ftpserver";

//    @Option(name="-host")
    private String host = "localhost";

//    @Option(name="-username")
    private String username = "ericlu";

//    @Option(name="-password")
    private String password = "1943L1944t$&@";
    DirectoryPoller dp;

    public void stop(){
        dp.stop();

    }



    @PostConstruct
    private void doMain() throws InterruptedException {


//        System.out.println("monitoring directory: " + path);
        log.info("monitoring directory: {} ",  path);
        PolledDirectory polledDirectory = new SFtpDirectory(host, path, username, password);

         dp = DirectoryPoller.newBuilder()
                .addPolledDirectory(polledDirectory)
                .addListener(new MyListener())
                .enableFileAddedEventsForInitialContent()
//            .setPollingInterval(10, TimeUnit.MINUTES)
                .setPollingInterval(10, TimeUnit.SECONDS)
                .start();

//        TimeUnit.HOURS.sleep(2);
//
//        dp.stop();
        }

}
