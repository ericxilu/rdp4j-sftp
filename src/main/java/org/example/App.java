package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

//@EnableAutoConfiguration
@SpringBootApplication
//@PropertySource("classpath:application.properties")
//@ComponentScan({"org.example"})
//@Configuration
public class App
{
//    public static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Bean(name = "dataSourceEric")
    @ConfigurationProperties(prefix = "datasource.eric")
    public DataSource dataSourceEric() {return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplateEric")
    public JdbcTemplate jdbcTemplateEric(@Qualifier("dataSourceEric") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "namedParameterJdbcTemplateEric")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateEric(@Qualifier("dataSourceEric") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
