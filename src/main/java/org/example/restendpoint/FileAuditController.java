package org.example.restendpoint;


import lombok.extern.slf4j.Slf4j;
import org.example.data.FileAuditService;
import org.example.sftp.SftpDirectoryPuller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class FileAuditController {

    private final FileAuditService fileAuditService;
    private final SftpDirectoryPuller sftpDirectoryPuller;


    public FileAuditController(final FileAuditService fileAuditService,
                               final SftpDirectoryPuller sftpDirectoryPuller) {
        this.fileAuditService = fileAuditService;
        this.sftpDirectoryPuller = sftpDirectoryPuller;
    }

    @GetMapping("/fileaudit")
    public ResponseEntity<List<Map<String, Object>>> getFileAudit(){
        log.info("getting file audit");

        try{
            return new ResponseEntity<>(fileAuditService.getFileAudits(), HttpStatus.OK);

        }finally {
            log.info("getFileAudit completed");

        }
    }

    @GetMapping("/stopSftpPuller")
    public ResponseEntity<String> stopFtpPuller(){
        log.info("stopping ftp puller");
        try {
            sftpDirectoryPuller.stop();
            log.info("sftp puller stopped");
            return new ResponseEntity<>("sftp puller stopped", HttpStatus.OK);
        }finally {
            log.info("stopFtpPuller completed");
        }
    }

}
