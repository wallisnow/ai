package com.ai.sys.train;

import com.ai.sys.model.Command;
import com.ai.sys.model.entity.Algo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ContainerDatasetProcessor implements DatasetProcessor {

    @Value("${workspace.algo}")
    private String algoDir;

    @Value("${workspace.result}")
    private String resultDir;

    @Value("${workspace.resultsuffix}")
    private String resultSuffix;

    @Value("${workspace.model}")
    private String modelDir;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private final static String PYTHON_EXECUTOR = "python";
    private final static String PARAM_SPLITER = " ";

    @Override
    public void process(Command command) throws InterruptedException, IOException, ExecutionException {
        this.boostrapContainer(command.getAlgo());
    }

    public void boostrapContainer(@NonNull Algo algo) {

        DefaultDockerClientConfig config
                = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withRegistryEmail("info@baeldung.com")
//                .withRegistryPassword("baeldung")
//                .withRegistryUsername("baeldung")
//                .withDockerCertPath("/home/baeldung/.docker/certs")
//                .withDockerConfig("/home/baeldung/.docker/")
//                .withDockerTlsVerify("1")
                .withDockerHost("tcp://localhost:2375").build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        String workspace = algoDir + '/' + algo.getId();
        String model = modelDir + "/" + algo.getId() + "/" + dateFormat.format(new Date());
        String dataset = algo.getDataSet().getPath();

        CreateContainerResponse container
                = dockerClient.createContainerCmd("anibali/pytorch:1.10.2-cuda11.3")
                .withCmd("/bin/bash -c \"cd /workspace && python main.py\"\n")
                .withName("mnist-" + algo.getId())
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

        dockerClient.removeContainerCmd(container.getId()).exec();
    }

}
