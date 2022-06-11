package com.ai.sys.config;

import com.ai.sys.train.ContainerDatasetProcessor;
import com.ai.sys.train.DatasetProcessor;
import com.ai.sys.train.MessageTypeDataProcessor;
import com.ai.sys.train.SimpleDatasetProcessor;
import com.ai.sys.train.mq.KafKaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class ProcessConfig {
    @Bean
    public DatasetProcessor datasetProcessor() {
        return new SimpleDatasetProcessor();
    }

    @Bean
    public DatasetProcessor containerDatasetProcessor() {
        return new ContainerDatasetProcessor();
    }

    @Bean
    public DatasetProcessor messageTypeDataProcessor(KafKaProducerService kafKaProducerService) {
        return new MessageTypeDataProcessor(kafKaProducerService);
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

    @Bean
    public RecordMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
