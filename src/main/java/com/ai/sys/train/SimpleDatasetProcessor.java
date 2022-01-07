package com.ai.sys.train;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Log4j2
public class SimpleDatasetProcessor implements DatasetProcessor {

    @Override
    public void process(String algoPath, String datasetPath) throws InterruptedException, IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", algoPath, datasetPath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        String result = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        process.waitFor();
        log.debug(result);
        System.out.println(result);
    }
}
