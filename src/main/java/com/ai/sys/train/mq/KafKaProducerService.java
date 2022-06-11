package com.ai.sys.train.mq;

import com.ai.sys.model.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafKaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ListenableFuture<SendResult<String, String>> sendMessage(Command command) throws JsonProcessingException {
        String s = objectMapper.writeValueAsString(command);
        log.info(String.format("Message sent -> %s", command));
        return this.kafkaTemplate.send(MqConstants.TOPIC_NAME, s);
    }
}
