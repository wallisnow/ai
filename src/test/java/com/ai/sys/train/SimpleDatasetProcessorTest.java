package com.ai.sys.train;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SimpleDatasetProcessorTest {

    @Test
    void processHappyPath() throws InterruptedException, IOException {
        SimpleDatasetProcessor simpleDatasetProcessor = new SimpleDatasetProcessor();
        simpleDatasetProcessor.process("./src/test/resources/algo/print.py",
                null,
                "./src/test/resources/sample/heart.csv");
    }

    @Test
    void process() {
        SimpleDatasetProcessor simpleDatasetProcessor = new SimpleDatasetProcessor();
        Assertions.assertThrows(NullPointerException.class, () -> {
            simpleDatasetProcessor.process(null, null, "data_set.csv");
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            simpleDatasetProcessor.process("algo", null, null);
        });
    }
}