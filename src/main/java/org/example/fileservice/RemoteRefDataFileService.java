package org.example.fileservice;

import com.github.drapostolos.rdp4j.spi.FileElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class RemoteRefDataFileService {

    @Value("${localRefData.path}")
    private String localRefDataFilePath;

    private LocalRefDataFileService localRefDataFileService;

    public RemoteRefDataFileService(final LocalRefDataFileService localRefDataFileService){
        this.localRefDataFileService = localRefDataFileService;
    }


    public void transportRemoteRefDataFile(FileElement file) throws IOException {

        if (!filterRemoreDataFile(file)) {
            Set<String> localFiles = localRefDataFileService.getExisingRefDataFiles(localRefDataFilePath);
            if (!localFiles.isEmpty()) {
                if (!localFiles.contains(file.getName())) {
                    log.info("Droit CCP ref data file {} added on ftp server is new, ftping... ", file.getName());
                } else {
                    log.info("Droit CCP ref data file {} added on ftp server already exist ", file.getName());
                }
            }
        }
    }

    private static boolean filterRemoreDataFile (FileElement fileElement) {
        return (".".equals(fileElement.getName()) || "..".equals(fileElement.getName()));
    }
}
