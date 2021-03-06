package org.example.fileservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.drapostolos.rdp4j.spi.FileElement;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.example.client.FileAuditServiceClient;
import org.example.model.FileAudit;
import org.example.sftp.SftpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

@Component
@Slf4j
public class RemoteRefDataFileService {

    @Value("${sftp.path}")
    private String remoteFilePath;

    private LocalRefDataFileService localRefDataFileService;

    private SftpClient sftpClient;

    private FileDecompressor fileDecompressor;

    private FileAuditServiceClient fileAuditServiceClient;

    public RemoteRefDataFileService(final LocalRefDataFileService localRefDataFileService,
                                    final SftpClient sftpClient,
                                    final TarGzFileDecompressor tarGzFileDecompressor,
                                    final FileAuditServiceClient fileAuditServiceClient) {
        this.localRefDataFileService = localRefDataFileService;
        this.sftpClient = sftpClient;
        this.fileDecompressor = tarGzFileDecompressor;
        this.fileAuditServiceClient = fileAuditServiceClient;
    }

    public void processRemoteRefDataFile (FileElement file) throws SftpException, IOException {
        if (transportRemoteRefDataFile(file)) {
            decompressRefDataFile(file);
            archiveRefDataFile(file);
            refreshRefData(file);
        }
    }

    private boolean transportRemoteRefDataFile(FileElement file) throws IOException, SftpException {
        log.info("transport ref data file {}", file.getName());
        Set<String> localFiles = localRefDataFileService.getExisingRefDataFiles();
        if (localFiles.isEmpty() || !localFiles.contains(file.getName())) {
            downLoadFile(file);
            return true;
        } else {
            log.info("Droit CCP ref data file {} added on ftp server already exist ", file.getName());
            return false;
        }
    }

    private void downLoadFile(FileElement file) throws IOException, SftpException {
        log.info("Droit CCP ref data file {} added on ftp server is new, ftping... ", file.getName());
        sftpClient.connect();
        sftpClient.downloadFile(getRemoteFileFullPath(file.getName()), getLocalFileFullPath(file.getName()));
        sftpClient.disconnect();
        log.info("Droit CCP ref data file {} downloaded ", file.getName());
    }

    private void decompressRefDataFile (FileElement file) throws IOException {
        fileDecompressor.decompress(Paths.get(getLocalFileFullPath(file.getName())), Paths.get(localRefDataFileService.getLocalRefDataPath()));
        log.info("ref data file {} decompressed", file);
    }

    private void archiveRefDataFile (FileElement file) throws IOException {
        localRefDataFileService.archiveRefDataFile(file.getName());
        log.info("ref data file {} archived", file);
    }

    private void refreshRefData (FileElement file) throws JsonProcessingException {
        fileAuditServiceClient.addFileAudit(new FileAudit(file.getName()));
        log.info("ref data file {} refreshed", file);
    }

    private String getRemoteFileFullPath(String remoteFileName) {
        return remoteFilePath + "/" + remoteFileName;
    }

    private String getLocalFileFullPath(String localFileName) {
        return localRefDataFileService.getLocalRefDataPath() + "/" + localFileName;
    }
}
