package com.ai.sys.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ModelServiceImplTest {

    @TempDir
    Path tempDir;
    Path tempFile;

    ModelService modelService = new ModelServiceImpl();

    @BeforeEach
    void setUp() throws IOException {
        //Files.createDirectories(Paths.get("a/b/c"));
        tempFile = Files.createFile(tempDir.resolve("d.txt"));
        Files.writeString(tempFile, "Hello World");
    }

    @Test
    void deleteFileByPath() throws IOException {
        Assertions.assertTrue(Files.exists(tempFile));
        boolean b = modelService.deleteByPath(tempFile.toString());
        Assertions.assertTrue(b);
        Assertions.assertFalse(Files.exists(tempFile));
    }

    @Test
    void deleteDirByPath(@TempDir Path b) throws IOException {
        String path = b.toString() + "/path/to/directory";
        Path dir = Paths.get(path);
        Files.createDirectories(dir);
        Files.isDirectory(dir);
        Assertions.assertTrue(Files.isDirectory(dir));
        modelService.deleteByPath(path);
        Assertions.assertTrue(Files.notExists(dir));
    }
}