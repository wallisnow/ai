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
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class SimpleDatasetProcessor implements DatasetProcessor {

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

    private final static String DOCKER_COMPOSE_TEMPLATE =
            "version: \"3\"\n" +
            "services:\n" +
            "    mnist:\n" +
//          "        container_name: mnist\n" +
            "        container_name: %s\n" +
            "        image: anibali/pytorch:1.10.2-nocuda\n" +
            "        volumes:\n" +
//          "            - \"./:/workspace\"\n" +
//          "            - \"/Users/liujun/Desktop/AI/results:/result\"\n" +
//          "            - \"/Users/liujun/Desktop/AI/datasets:/dataset\"\n" +
//          "            - \"/Users/liujun/Desktop/AI/models/1:/model\"\n" +
            "            - \"%s:/workspace\"\n" +
            "            - \"%s:/result\"\n" +
            "            - \"%s:/dataset\"\n" +
            "            - \"%s:/model\"\n" +
            "        command: /bin/bash -c \"cd /workspace && python main.py\"\n" +
            "        environment:\n" +
//            "          - JOB_ID=2";
                    "          - JOB_ID=%d";


    //TODO
    // 待重构，目前这部分是hard code，docker镜像名称是写死的
    // 后期可能需要支持用户自己选择运行的docker镜像
    @Override
    public void process(Command command) {
        try {
            unzipSourceCode(command.getAlgo());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        // add docker compose file
        ProcessBuilder processBuilder = createContainerProcessBuilder(command.getAlgo());

        CompletableFuture.runAsync(() -> {
                    log.debug(Thread.currentThread().getName() + "\t python process future run ... ...");
                    try {
                        //use bootstrap docker client here
                        //boostrapContainer(algo);
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

    private ProcessBuilder createContainerProcessBuilder(Algo algo) {
        BufferedWriter writer;
        try {
            Date date = new Date();
            writer = new BufferedWriter(new FileWriter(algoDir + '/' + algo.getId() + "/docker-compose.yml"));

            writer.write(
                    String.format(
                            DOCKER_COMPOSE_TEMPLATE,
                            "mnist-" + algo.getId(),                                      // name
                            algoDir + '/' + algo.getId(),                               // workspace
                            resultDir,                                                  // result
                            algo.getDataSet().getPath(),                                // dataset
                            modelDir + "/" + algo.getId() + "/" + dateFormat.format(date),   // model
                            algo.getId()
                    ));

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // launch docker
        ProcessBuilder processBuilder = new ProcessBuilder("/usr/local/bin/docker-compose", "up", "-d");
        processBuilder.directory(new File(algoDir + '/' + algo.getId()));
        processBuilder.redirectErrorStream(true);
        return processBuilder;
    }

    private void unzipSourceCode(Algo algo) throws IOException {
        File destDir = new File(algoDir + '/' + algo.getId());
        if (destDir.exists()) {
            FileSystemUtils.deleteRecursively(destDir);
        }

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(algo.getPath()));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
