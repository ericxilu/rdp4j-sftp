package org.example.fileservice;

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
public class LocalRefDataFileService {


    public Set<String> getExisingRefDataFiles (String localRefDataPath) throws IOException {

        try (Stream<Path> stream = list(Paths.get(localRefDataPath))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

}
