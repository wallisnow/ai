package com.ai.sys.config;

import com.ai.sys.train.DatasetProcessor;
import com.ai.sys.train.SimpleDatasetProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {
    @Bean
    public DatasetProcessor datasetProcessor() {
        return new SimpleDatasetProcessor();
    }

    /**
     * eliminate Java 8 date/time type `java.time.Instant` not supported error
     */
    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
