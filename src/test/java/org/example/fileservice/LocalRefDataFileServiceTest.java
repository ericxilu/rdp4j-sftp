package org.example.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LocalRefDataFileServiceTest {

    String localFilePath = "/Users/ericlu/refdata";
    String localFileArchivePath = "/Users/ericlu/refdata/archive";
    LocalRefDataFileService localRefDataFileService = new LocalRefDataFileService(localFilePath, localFileArchivePath);

    @Test
    void getExisingRefDataFiles() {
        try {
            log.info("local ref data files {}",localRefDataFileService.getExisingRefDataFiles());
            assert(localRefDataFileService.getExisingRefDataFiles().contains("1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}