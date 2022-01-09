package com.ai.sys.controller;

import com.ai.sys.model.Command;
import com.ai.sys.train.DatasetProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exec")
public class ExecutorController {
    private final DatasetProcessor datasetProcessor;

    @PostMapping("/")
    public @ResponseBody
    ResponseEntity<HttpStatus> execute(@RequestBody Command command) {
        try {
            datasetProcessor.process(command.getAlgoPath(), command.getParams(), command.getDataSetPath());
            return ResponseEntity.accepted().build();
        } catch (InterruptedException | IOException e) {
            log.debug(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
