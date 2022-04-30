package com.ai.sys.train;

import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.DataSet;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class SimpleDatasetProcessorTest {

    public static final String TEST_ALGOS_TEST_ZIP = "D:\\code\\ai\\src\\test\\algos\\test.zip";
    public static final String TEST_DATASET_HEART_CSV = "D:\\code\\ai\\src\\test\\dataset\\heart.csv";
    public static final long ALGO_ID = 1;

    @Value("${workspace.result}")
    private String resultDir;

    @Value("${workspace.resultsuffix}")
    private String resultSuffix;

    @Autowired
    DatasetProcessor simpleDatasetProcessor;

    @BeforeEach
    void setUp() throws IOException {
        FileUtils.cleanDirectory(new File(resultDir));
        Assertions.assertTrue(isEmpty(Paths.get(resultDir)));
    }

    @Test
    void testProcess() throws IOException, ExecutionException, InterruptedException {
        Algo build = Algo.builder().id(ALGO_ID)
                .name("algo")
                .path(TEST_ALGOS_TEST_ZIP)
                .dataSet(DataSet.builder()
                        .name("dataset")
                        .path(TEST_DATASET_HEART_CSV).build())
                .build();
        simpleDatasetProcessor.process(build, List.of());

        await().atMost(20, TimeUnit.SECONDS).until(() -> {
            String rest = resultDir + "/" + ALGO_ID + resultSuffix;
            Path path = Paths.get(rest);
            return (Files.exists(path));
        });
    }

    public boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return !entries.findFirst().isPresent();
            }
        }

        return false;
    }
}

