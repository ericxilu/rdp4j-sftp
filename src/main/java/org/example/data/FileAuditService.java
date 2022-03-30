package org.example.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:sql.properties")

@Slf4j
@Component
public class FileAuditService {
    @Value("${sql.GetFileAudit}")
    private String sqlGetFileAudit;

    @Value("${sql.AddFileAudit}")
    private String sqlAddFileAudit;

    private final JdbcTemplate jdbcTemplateEric;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplateEric;



    public FileAuditService(@Qualifier("jdbcTemplateEric") final JdbcTemplate jdbcTemplateEric,
                            @Qualifier("namedParameterJdbcTemplateEric") final NamedParameterJdbcTemplate namedParameterJdbcTemplateEric) {
        this.jdbcTemplateEric = jdbcTemplateEric;
        this.namedParameterJdbcTemplateEric = namedParameterJdbcTemplateEric;
    }

    public List<Map<String, Object>> getFileAudits(){
        return jdbcTemplateEric.queryForList(sqlGetFileAudit);
    }
    public void addFileAudits(Map<String, Object> params){
        Map<String, Object> sqlParameters = new HashMap<>();


         namedParameterJdbcTemplateEric.update(sqlAddFileAudit, new MapSqlParameterSource( params));
    }



}
