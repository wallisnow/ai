package com.ai.sys.train;

import com.ai.sys.model.entity.Algo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

class SimpleDatasetProcessorTest {

    @Test
    void processHappyPath() throws InterruptedException, IOException, ExecutionException {
        SimpleDatasetProcessor simpleDatasetProcessor = new SimpleDatasetProcessor();
        Algo algo = new Algo();
        algo.setPath("./src/test/resources/algo/print.py");
        algo.getDataSet().setPath( "./src/test/resources/sample/heart.csv");
        simpleDatasetProcessor.process(algo, null);
    }

    @Test
    void process() {
        SimpleDatasetProcessor simpleDatasetProcessor = new SimpleDatasetProcessor();

        Algo algo = new Algo();
        algo.getDataSet().setPath("data_set.csv");

        Assertions.assertThrows(NullPointerException.class, () -> {
            simpleDatasetProcessor.process(algo, null);
        });

        algo.setPath("algo");
        Assertions.assertThrows(NullPointerException.class, () -> {
            simpleDatasetProcessor.process(algo, null);
        });
    }
}