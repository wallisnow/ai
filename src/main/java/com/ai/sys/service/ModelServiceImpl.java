package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.Model;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    @Value("${workspace.model}")
    private String modelDir;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<Model> findAll() {
        List<Model> models = new ArrayList<Model>();
        populateDirectoryContent(models, Paths.get(modelDir));
        return models;
    }

    @Override
    public boolean deleteByPath(String path) throws ResourceOperationException, IOException {
        return FileSystemUtils.deleteRecursively(Paths.get(path));
    }

    private void populateDirectoryContent(List<Model> parent, Path parentPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(parentPath)) {
            for (Path path : stream) {
                if(Files.isHidden(path))
                    continue;

                Model currentDirectory = new Model();
                //TODO jun:向后兼容
                // 我也忘了这个todo是要干啥。。。先保留着吧
                currentDirectory.setId(UUID.randomUUID().getLeastSignificantBits());
                currentDirectory.setName(path.getFileName().toString());
                currentDirectory.setLastModified(
                    dateFormat.format(path.toFile().lastModified())
                );
                currentDirectory.setPath(path.toString());
                parent.add(currentDirectory);

                if (Files.isDirectory(path)) {
                    List<Model> children = new ArrayList<Model>();
                    currentDirectory.setChildren(children);
                    populateDirectoryContent(children, path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
