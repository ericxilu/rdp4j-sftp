package org.example.sftp;

import com.github.drapostolos.rdp4j.spi.FileElement;
import org.springframework.stereotype.Component;

@Component
public class UnixSftpFileFilter implements SftpFileFilter {
    static String unixLocalDir = ".";
    static String unixParentDir = "..";

    @Override
    public boolean filterRemoreDataFile(FileElement fileElement) {
        return (!unixLocalDir.equals(fileElement.getName()) && !unixParentDir.equals(fileElement.getName()));
    }
}
