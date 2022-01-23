package com.ai.sys.train;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Log4j2
public class SimpleDatasetProcessor implements DatasetProcessor {

    private final static String PYTHON_EXECUTOR = "python";
    private final static String PARAM_SPLITER = " ";

    @Override
    public void process(@NonNull String algoPath, List<String> params, @NonNull String datasetPath) throws InterruptedException, IOException, ExecutionException {
        List<String> paramList = Optional.ofNullable(params).orElseGet(ArrayList::new);
        paramList.add(datasetPath);
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_EXECUTOR, algoPath, String.join(PARAM_SPLITER, paramList));
        processBuilder.redirectErrorStream(true);

        CompletableFuture.runAsync(() -> {
                    log.debug(Thread.currentThread().getName() + "\t python process future run ... ...");
                    try {
                        Process process = processBuilder.start();
                        InputStream inputStream = process.getInputStream();
                        String result = new BufferedReader(new InputStreamReader(inputStream))
                                .lines().collect(Collectors.joining("\n"));
                        process.waitFor();
                        log.debug(result);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                })
                .whenComplete((v, t) -> log.debug(Thread.currentThread().getName() + "\t python process Done" + " v " + v + " t " + t))
                .exceptionally(throwable -> {
                    log.error(throwable.getMessage());
                    return null;
                });
    }
}
