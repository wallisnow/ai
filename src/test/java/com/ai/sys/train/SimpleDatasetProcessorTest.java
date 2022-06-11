package com.ai.sys.train;

import com.ai.sys.model.Command;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.DataSet;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

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
@ActiveProfiles("test")
class SimpleDatasetProcessorTest extends AbstractProcessorTest{

    @Value("${workspace.result}")
    private String resultDir;

    @Value("${workspace.resultsuffix}")
    private String resultSuffix;


    @Value("${workspace.model}")
    private String modelDir;

    @Qualifier("datasetProcessor")
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
        Command command = new Command(build, null, "");
        simpleDatasetProcessor.process(command);

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

