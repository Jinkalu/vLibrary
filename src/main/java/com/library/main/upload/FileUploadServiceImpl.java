package com.library.main.upload;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final Path rootDir = Paths.get("uploads");

    @Override
    public void init() {

        try {
            File tempDir = new File(rootDir.toUri());
            boolean dirExists = tempDir.exists();
            if (!dirExists) {
                Files.createDirectory(rootDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating directory");
        }

    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(),
                    this.rootDir.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("Error uploading files");
        }
    }

    @Override
    public Resource getFileByName(String fileName) {
        try {
            Path filePath = rootDir.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootDir.toFile());
    }

    @Override
    public Stream<Path> loadAllFiles() {

        try {
            return Files.walk(this.rootDir, 1)
                    .filter(path -> !path.equals(this.rootDir))
                    .map(this.rootDir::relativize);
        } catch (IOException ex) {
            throw new RuntimeException("could not load file");
        }

    }

}
