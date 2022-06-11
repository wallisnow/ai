package com.ai.sys.train;

import com.ai.sys.model.Command;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public interface DatasetProcessor {
    void process(Command command) throws InterruptedException, IOException, ExecutionException;
}
