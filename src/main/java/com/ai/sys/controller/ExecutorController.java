package com.ai.sys.controller;

import com.ai.sys.model.Command;
import com.ai.sys.service.AlgoService;
import com.ai.sys.train.DatasetProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exec")
public class ExecutorController {

    private final DatasetProcessor messageTypeDataProcessor;
    private final AlgoService algoService;

    @PostMapping("/algo")
    public @ResponseBody
    ResponseEntity<HttpStatus> execute(@RequestBody Command command) {
        try {
            algoService.updateCompleteStatus(command.getAlgo().getId(), false);
            messageTypeDataProcessor.process(command);
            return ResponseEntity.accepted().build();
        } catch (InterruptedException | IOException e) {
            setAlgoToCompleted(command.getAlgo().getId());
            log.debug(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().build();
        } catch (ExecutionException e) {
            setAlgoToCompleted(command.getAlgo().getId());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private void setAlgoToCompleted(final Long id) {
        algoService.updateCompleteStatus(id, true);
    }
}
