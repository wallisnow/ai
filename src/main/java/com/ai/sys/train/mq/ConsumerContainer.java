package com.ai.sys.train.mq;

import com.ai.sys.model.Command;
import com.ai.sys.model.entity.Algo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void run(Command command){
        Algo algo = command.getAlgo();
        DefaultDockerClientConfig config = DefaultDockerClientConfig
                .createDefaultConfigBuilder()
                .withDockerHost(dockerHost).build();

        //执行完成后删除容器
        HostConfig hostConfig = HostConfig
                .newHostConfig()
                .withAutoRemove(true);

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        String workspace = algoDir + '/' + algo.getId();
        String model = modelDir + "/" + algo.getId() + "/" + dateFormat.format(new Date());
        String dataset = algo.getDataSet().getPath();

        CreateContainerResponse container
                = dockerClient.createContainerCmd(command.getImage())
                .withHostConfig(hostConfig)
//        CreateContainerResponse container
//                = dockerClient.createContainerCmd("anibali/pytorch:1.10.2-cuda11.3")
//                .withCmd("/bin/bash -c \"cd /workspace && python main.py\"\n")
                .withCmd(command.getFullCmd())
                .withHostName("ai")
                .withEnv("JOB_ID=" + algo.getId())
                //.withPortBindings(PortBinding.parse("9999:27017"))
                .withHostConfig(HostConfig.newHostConfig().withBinds(
                        Bind.parse(workspace + ":/workspace"),
                        Bind.parse(resultDir + ":/result"),
                        Bind.parse(dataset + ":/dataset"),
                        Bind.parse(model + ":/model")
                ))
                .exec();

        log.info("Dataset training request received, running in container: {}", container.getId());
        //dockerClient.removeContainerCmd(container.getId()).exec();
    }
}
