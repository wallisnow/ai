package com.ai.sys.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileTransferService {

    void init() throws IOException;

    String save(MultipartFile file) throws IOException;

    Resource load(String filename) throws MalformedURLException;

    void deleteAll();

    Stream<Path> loadAll() throws IOException;
}
