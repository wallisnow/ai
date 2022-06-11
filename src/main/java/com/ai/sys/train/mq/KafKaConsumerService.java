package com.ai.sys.train.mq;

import com.ai.sys.model.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafKaConsumerService {
    private final ObjectMapper objectMapper;
    private final ConsumerContainer consumerContainer;

    @KafkaListener(topics = MqConstants.TOPIC_NAME, groupId = MqConstants.GROUP_ID)
    public void consume(ConsumerRecord<String, String> bookConsumerRecord) throws JsonProcessingException {
        Command command = objectMapper.readValue(bookConsumerRecord.value(), Command.class);
        log.info(String.format("Message received -> %s", command.toString()));
        consumerContainer.run(command);
    }
}
