package org.example.sftp;

import com.github.drapostolos.rdp4j.*;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.example.fileservice.RemoteRefDataFileService;

import java.io.IOException;

@Slf4j
public class SftpDirectoryPullerListener implements DirectoryListener, IoErrorListener, InitialContentListener {

    private RemoteRefDataFileService remoteRefDataFileService;

    private SftpFileFilter sftpFileFilter;

    public SftpDirectoryPullerListener(final RemoteRefDataFileService remoteRefDataFileService,
                                       final SftpFileFilter unixSftpFIleFilter) {
        this.remoteRefDataFileService = remoteRefDataFileService;
        this.sftpFileFilter = unixSftpFIleFilter;
    }

    @Override
    public void fileAdded(FileAddedEvent event) {
        try {
            if (sftpFileFilter.filterRemoreDataFile(event.getFileElement())) {
                log.info("new file  {} added on sftp server ", event.getFileElement());
                remoteRefDataFileService.processRemoteRefDataFile(event.getFileElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SftpException e) {
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
        log.error("I/O error raised!");
    }

    //      @Override
    public void initialContent(InitialContentEvent event) {
    }
}