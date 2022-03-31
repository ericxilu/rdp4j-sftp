package org.example.restendpoint;


import lombok.extern.slf4j.Slf4j;
import org.example.data.FileAuditService;
import org.example.model.FileAudit;
import org.example.sftp.SftpDirectoryPuller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping(value = "/addfileaudit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<FileAudit> addFileAudit(@RequestBody FileAudit fileAudit){
        log.info("adding file audit");

        try{
            Map<String, Object> params = new HashMap<>();
            params.put("fileName",fileAudit.getFileName());
            fileAuditService.addFileAudits(params);
            return new ResponseEntity<>(new FileAudit(fileAudit.getFileName()), HttpStatus.OK);

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
