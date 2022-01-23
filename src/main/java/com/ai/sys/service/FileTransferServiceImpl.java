package com.ai.sys.service;

import com.ai.sys.config.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Log4j2
@Service
public class FileTransferServiceImpl implements FileTransferService {

    private final Path root = Paths.get(Constant.FILE_ROOT_PATH);

    @Override
    public void init() throws IOException {
        Files.createDirectories(root);
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Path resolve = root.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        if (Files.exists(Paths.get(resolve.toString()))) {
            log.debug("uploading same with same file name, will create a prefix...");
            String generatedString = RandomStringUtils.randomAlphanumeric(5);
            Path newPath = root.resolve(generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(inputStream, newPath);
            return root.resolve(newPath).toString();
        } else {
            Files.copy(inputStream, resolve);
            return root.resolve(file.getOriginalFilename()).toString();
        }
    }

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path file = root.resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() throws IOException {
        return Files.walk(root, 1)
                .filter(path -> !path.equals(root))
                .map(root::relativize);
    }
}
