package com.library.main.upload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;


public interface FileUploadService {
    void init();
    void save(MultipartFile file);
    Resource getFileByName(String fileName);
    Stream<Path> loadAllFiles();
    void deleteAll();
}
