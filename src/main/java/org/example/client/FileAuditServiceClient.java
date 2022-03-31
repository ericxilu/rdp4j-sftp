package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.model.FileAudit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class FileAuditServiceClient {
    @Value("${url.fileAuditController.addFileAudit}")
    private String addFileAuditUrl;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public FileAuditServiceClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void addFileAudit(FileAudit fileAudit) throws JsonProcessingException {
        log.info("requesting add file audit {} with url {} ", fileAudit, this.addFileAuditUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requset = new HttpEntity<>(objectMapper.writeValueAsString(fileAudit), httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(addFileAuditUrl, requset, String.class);
        log.info("add FileAudit with response {} ", responseEntity);

    }
}
