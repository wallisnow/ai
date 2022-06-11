package com.ai.sys.train;

import com.ai.sys.model.Command;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.train.mq.KafKaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = {KafKaProducerService.class})
public class MessageTypeDataProcessorTest {

    @Autowired
    private KafKaProducerService kafKaProducerService;

    private final MessageTypeDataProcessor messageTypeDataProcessor = new MessageTypeDataProcessor(kafKaProducerService);

    @Test
    void process() throws IOException, ExecutionException, InterruptedException {
        Command command = new Command(Algo.builder()
                .id(1)
                .name("myalgo")
                .build(), null, "");
        messageTypeDataProcessor.process(command);
    }
}