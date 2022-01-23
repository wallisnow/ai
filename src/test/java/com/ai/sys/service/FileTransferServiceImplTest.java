package com.ai.sys.service;

import com.ai.sys.config.Constant;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

class FileTransferServiceImplTest extends ServiceTest {

    FileTransferServiceImpl fileTransferService = new FileTransferServiceImpl();

    @BeforeEach
    void setUp() {
        try {
            fileTransferService.deleteAll();
            fileTransferService.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() throws IOException {
        String originalFilename = "orig.txt";
        MultipartFile file = new MockMultipartFile("file", originalFilename, null, "bar".getBytes());
        String path = fileTransferService.save(file);
        Assertions.assertTrue(Path.of(path).toString().contains(Constant.FILE_ROOT_PATH));
        Assertions.assertTrue(Path.of(path).toString().contains(originalFilename));
    }
}