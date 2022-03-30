package org.example.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LocalRefDataFileServiceTest {

    LocalRefDataFileService localRefDataFileService = new LocalRefDataFileService();

    @Test
    void getExisingRefDataFiles() {
        String testPath = "/Users/ericlu/refdata";
        try {
            log.info("files under path {}: {}", testPath,localRefDataFileService.getExisingRefDataFiles(testPath));
            assert(localRefDataFileService.getExisingRefDataFiles(testPath).contains("1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}