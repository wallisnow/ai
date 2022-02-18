package com.ai.sys.config;

import com.ai.sys.service.FileTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventListenerCustomBean {

    private final FileTransferService fileTransferService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
        log.debug("start app, create file upload location");
        //storageService.deleteAll();
        fileTransferService.init();
    }
}
