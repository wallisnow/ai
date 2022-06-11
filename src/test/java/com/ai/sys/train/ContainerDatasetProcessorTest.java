package com.ai.sys.train;

import com.ai.sys.model.Command;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@ActiveProfiles("test")
class ContainerDatasetProcessorTest extends AbstractProcessorTest {

    @Value("${workspace.result}")
    private String resultDir;

    @Value("${workspace.resultsuffix}")
    private String resultSuffix;


    @Value("${workspace.model}")
    private String modelDir;

    @Autowired
    DatasetProcessor containerDatasetProcessor;

    @Test
    void boostrapContainer() throws IOException, ExecutionException, InterruptedException {
        Algo build = Algo.builder().id(ALGO_ID)
                .name("algo")
                .path(TEST_ALGOS_TEST_ZIP)
                .dataSet(DataSet.builder()
                        .name("dataset")
                        .path(TEST_DATASET_HEART_CSV).build())
                .build();
        Command command = new Command(build, null, "");
        containerDatasetProcessor.process(command);
    }
}