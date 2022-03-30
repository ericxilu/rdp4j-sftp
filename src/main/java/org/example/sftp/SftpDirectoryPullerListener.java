package org.example.sftp;

import com.github.drapostolos.rdp4j.*;
import lombok.extern.slf4j.Slf4j;
import org.example.fileservice.RemoteRefDataFileService;

import java.io.IOException;

@Slf4j
public class SftpDirectoryPullerListener implements DirectoryListener, IoErrorListener, InitialContentListener {

    private RemoteRefDataFileService remoteRefDataFileService;

    public SftpDirectoryPullerListener(final RemoteRefDataFileService remoteRefDataFileService) {
        this.remoteRefDataFileService = remoteRefDataFileService;
    }

            @Override
    public void fileAdded(FileAddedEvent event) {
        log.info("new file  {} added on sftp server ", event.getFileElement());
        try {
            remoteRefDataFileService.transportRemoteRefDataFile(event.getFileElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //        @Override
    public void fileRemoved(FileRemovedEvent event) {
    }

    //      @Override
    public void fileModified(FileModifiedEvent event) {
    }

    //      @Override
    public void ioErrorCeased(IoErrorCeasedEvent event) {
        log.error("I/O error ceased.");
    }

    //      @Override
    public void ioErrorRaised(IoErrorRaisedEvent event) {
        System.out.println("I/O error raised!");
        event.getIoException().printStackTrace();
    }

    //      @Override
    public void initialContent(InitialContentEvent event) {
    }
}