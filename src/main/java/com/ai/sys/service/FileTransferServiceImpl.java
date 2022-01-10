package com.ai.sys.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileTransferServiceImpl implements FileTransferService {

    private final Path root = Paths.get("uploads");

    @Override
    public void init() throws IOException {
        Files.createDirectory(root);
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
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
        return Files.walk(this.root, 1)
                .filter(path -> !path.equals(this.root))
                .map(this.root::relativize);
    }
}
