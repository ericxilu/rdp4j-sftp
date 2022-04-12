package org.example.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LocalRefDataFileServiceTest {

    String localFilePath = "/Users/ericlu/refdata";
    String localFileArchivePath = "/Users/ericlu/refdata/archive";
    LocalRefDataFileService localRefDataFileService = new LocalRefDataFileService(localFilePath, localFileArchivePath);



    @Test
    void getExisingRefDataFiles() {
        try {
            String testFileName = "1.txt";
            Path testFilePath = Paths.get(localFileArchivePath+"/"+testFileName);
            Files.createFile(testFilePath);
            log.info("local ref data files {}",localRefDataFileService.getExisingRefDataFiles());
            assert(localRefDataFileService.getExisingRefDataFiles().contains(testFileName));
            Files.delete(testFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}