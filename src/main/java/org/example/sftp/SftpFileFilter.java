package org.example.sftp;

import com.github.drapostolos.rdp4j.spi.FileElement;
import org.springframework.stereotype.Component;

@Component
public interface SftpFileFilter {
    boolean filterRemoreDataFile(FileElement fileElement);
}
