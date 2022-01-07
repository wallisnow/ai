package com.ai.sys.train;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface DatasetProcessor {
    void process(String algoPath, String datasetPath) throws InterruptedException, IOException;
}
