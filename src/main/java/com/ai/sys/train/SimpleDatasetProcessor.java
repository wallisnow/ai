package com.ai.sys.train;

import com.ai.sys.model.entity.Algo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class SimpleDatasetProcessor implements DatasetProcessor {

    @Value("workspace.algo")
    private String algoDir;

    private final static String PYTHON_EXECUTOR = "python";
    private final static String PARAM_SPLITER = " ";

    @Override
    public void process(@NonNull Algo algo, List<String> params) {
        //TODO jun: to test this method
        try {
            unzipSourceCode(algo);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        List<String> paramList = Optional.ofNullable(params).orElseGet(ArrayList::new);
        paramList.add(algo.getDataSet().getPath());
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_EXECUTOR, algo.getPath(), String.join(PARAM_SPLITER, paramList));
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

    private void unzipSourceCode(Algo algo) throws IOException {
        File destDir = new File(algoDir+'/'+algo.getId());
        if(destDir.exists())
            FileSystemUtils.deleteRecursively(destDir);

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
