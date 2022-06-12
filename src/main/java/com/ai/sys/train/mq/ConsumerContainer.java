package com.ai.sys.train.mq;

import com.ai.sys.model.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class ConsumerContainer {

    @Value("${workspace.algo}")
    private String algoDir;

    @Value("${workspace.result}")
    private String resultDir;

    @Value("${workspace.resultsuffix}")
    private String resultSuffix;

    @Value("${workspace.model}")
    private String modelDir;

    @Value("${algoconsumer.dockerhost}")
    private String dockerHost;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void run(Command command) throws IOException, InterruptedException {
        //docker run -it --rm --name my-running-script -v "$PWD":/usr/src/myapp -w /usr/src/myapp python:3 python your-daemon-or-script.py
        log.info("收到消息 ... ... {}", command.getAlgo().getName());
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("C:\\Program Files\\Git\\bin\\bash.exe", "D:\\code\\ai\\src\\main\\resources\\bin\\container_runner.sh", "test.py");
        Process process = processBuilder.start();
        process.waitFor();
    }
}
