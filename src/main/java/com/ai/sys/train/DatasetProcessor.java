package com.ai.sys.train;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface DatasetProcessor {
    void process(@NonNull String algoPath, List<String> params, @NonNull String datasetPath) throws InterruptedException, IOException;
}
