package org.example.fileservice;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.list;

@Component
@Getter
@Slf4j
public class LocalRefDataFileService {

    private String localRefDataPath;
    private String localRefDataArchivePath;

    public LocalRefDataFileService( @Value("${localRefData.path}") String localRefDataPath,
                                    @Value("${localRefData.pathArchive}") String localRefDataArchivePath) {
        this.localRefDataPath = localRefDataPath;
        this.localRefDataArchivePath = localRefDataArchivePath;
    }

    public Set<String> getExisingRefDataFiles () throws IOException {

        try (Stream<Path> stream = list(Paths.get(localRefDataPath))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    public void archiveRefDataFile(String file) throws IOException {
        Path source = Paths.get(localRefDataPath+"/"+file);
        Path target = Paths.get(localRefDataArchivePath +"/"+file);

        log.info("archiving file from {} to {}", source, target);
        Files.move(source, target);
        log.info("archived file from {} to {}", source, target);


    }

}
