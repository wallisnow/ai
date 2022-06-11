package com.ai.sys.train;

import com.ai.sys.model.Command;
import com.ai.sys.train.mq.KafKaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
public class MessageTypeDataProcessor implements DatasetProcessor {

    private final KafKaProducerService producerService;

    @Override
    public void process(Command command) throws InterruptedException, IOException, ExecutionException {
        producerService.sendMessage(command)
                .addCallback(result -> {
                            assert result != null;
                            log.info("生产者成功发送消息到 topic:{} partition:{} 的消息",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition());
                        },
                        ex -> log.error("生产者发送消失败，原因：{}", ex.getMessage()));
    }
}
