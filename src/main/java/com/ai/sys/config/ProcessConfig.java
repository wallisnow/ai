package com.ai.sys.config;

import com.ai.sys.train.DatasetProcessor;
import com.ai.sys.train.SimpleDatasetProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {
    @Bean
    public DatasetProcessor datasetProcessor() {
        return new SimpleDatasetProcessor();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
