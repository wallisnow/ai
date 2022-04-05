package com.ai.sys.train;

import com.ai.sys.model.entity.AlgoResult;
import com.ai.sys.service.AlgoResultService;
import com.ai.sys.service.AlgoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.*;

@Slf4j
public class FileListener extends FileAlterationListenerAdaptor {

    // 声明业务服务
    private AlgoService algoService;
    private AlgoResultService algoResultService;

    // 采用构造函数注入服务
    public FileListener(AlgoService algoService, AlgoResultService algoResultService) {
        this.algoService = algoService;
        this.algoResultService = algoResultService;
    }

    // 文件创建执行
    @Override
    public void onFileCreate(File file) {
        log.info("[新建]:" + file.getAbsolutePath());
        handleAlgoResult(file);
//        listenerService.doMain(file);
    }

    // 文件创建修改
    @Override
    public void onFileChange(File file) {
        log.info("[修改]:" + file.getAbsolutePath());
        // 触发业务
//        listenerService.doSomething();
        handleAlgoResult(file);
    }

    // 文件创建删除
    @Override
    public void onFileDelete(File file) {
        log.info("[删除]:" + file.getAbsolutePath());
    }

    // 目录创建
    @Override
    public void onDirectoryCreate(File directory) {
        log.info("[目录创建]:" + directory.getAbsolutePath());
    }

    // 目录修改
    @Override
    public void onDirectoryChange(File directory) {
        log.info("[目录修改]:" + directory.getAbsolutePath());
    }

    // 目录删除
    @Override
    public void onDirectoryDelete(File directory) {
        log.info("[目录删除]:" + directory.getAbsolutePath());
    }


    // 轮询开始
    @Override
    public void onStart(FileAlterationObserver observer) {
    }

    // 轮询结束
    @Override
    public void onStop(FileAlterationObserver observer) {
    }

    private void handleAlgoResult(final File resultFile){
        final String filePath = resultFile.getAbsolutePath();
        final Long id = Long.valueOf(filePath.replaceAll("\\D",""));

        algoService.updateCompleteStatus(id, true);

        final BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(resultFile));
            final String result = reader.readLine();
            final String[] results = result.split(" ");

            final AlgoResult algoResult = new AlgoResult();
            algoResult.setAlgoid(id);
            algoResult.setAccuracy(Float.valueOf(results[0]));
            algoResult.setCallback(Float.valueOf(results[1]));
            algoResultService.create(algoResult);

            reader.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}