package com.ai.sys.train;

import com.ai.sys.model.entity.Algo;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public interface DatasetProcessor {
    void process(@NonNull Algo algo, List<String> params) throws InterruptedException, IOException, ExecutionException;
}
