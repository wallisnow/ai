package com.ai.sys.train;

import com.ai.sys.model.entity.AlgoResult;
import com.ai.sys.service.AlgoResultService;
import com.ai.sys.service.AlgoService;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Component
public class FileListenerFactory {

    @Value("${workspace.result}")
    private String resultDir;

    @Value("${workspace.resultsuffix}")
    private String resultsuffix;

    // 设置轮询间隔
    private final long interval = TimeUnit.SECONDS.toMillis(1);

    @Autowired
    private AlgoService algoService;

    @Autowired
    private AlgoResultService algoResultService;

    public FileAlterationMonitor getAlgoResultMonitor() {
        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(resultsuffix));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        // 装配过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(resultDir), filter);
        // 向监听者添加监听器，并注入业务服务
        observer.addListener(new FileListener(algoService, algoResultService));

        // 返回监听者
        return new FileAlterationMonitor(interval, observer);
    }
}
