package com.ai.sys.train;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDatasetProcessorTest {

    @Test
    void process() throws InterruptedException, IOException {
        SimpleDatasetProcessor simpleDatasetProcessor = new SimpleDatasetProcessor();
        simpleDatasetProcessor.process("./src/test/resources/algo/print.py", "./src/test/resources/sample/heart.csv");
    }
}